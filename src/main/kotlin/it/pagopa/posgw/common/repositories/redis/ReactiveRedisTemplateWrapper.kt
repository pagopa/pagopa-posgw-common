package it.pagopa.posgw.common.repositories.redis

import java.time.Duration
import java.util.*
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.RecordId
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * This class is a [ReactiveRedisTemplate] wrapper class, used to centralize commons
 * ReactiveRedisTemplate operations
 *
 * @param <V> - the ReactiveRedisTemplate value type </V>
 */
abstract class ReactiveRedisTemplateWrapper<V>
protected constructor(
    reactiveRedisTemplate: ReactiveRedisTemplate<String, V>,
    keyspace: String,
    ttl: Duration
) where V : Any {
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, V>

    private val keyspace: String

    /**
     * Get the default configured TTL
     *
     * @return the default configured TTL
     */
    val defaultTTL: Duration

    /**
     * Primary constructor
     *
     * @param reactiveRedisTemplate underlying reactive Redis template
     * @param keyspace keyspace associated to this wrapper
     * @param ttl time to live for keys
     */
    init {
        Objects.requireNonNull(reactiveRedisTemplate, "ReactiveRedisTemplate null not valid")
        Objects.requireNonNull(keyspace, "Keyspace null not valid")
        Objects.requireNonNull(ttl, "TTL null not valid")
        this.reactiveRedisTemplate = reactiveRedisTemplate
        this.keyspace = keyspace
        this.defaultTTL = ttl
    }

    /**
     * Save the input entity into Redis. The entity TTL will be set to the default configured one
     *
     * @param value the entity to be saved
     * @return a [Mono] emitting `true` if the key was set, `false` otherwise
     */
    fun save(value: V): Mono<Boolean> {
        return save(value, this.defaultTTL)
    }

    /**
     * Save the input entity into Redis.
     *
     * @param value the entity to be saved
     * @param ttl the TTL for the entity to be saved. This parameter overrides the default TTL value
     * @return a [Mono] emitting `true` if the key was set, `false` otherwise
     */
    fun save(value: V, ttl: Duration): Mono<Boolean> {
        return reactiveRedisTemplate
            .opsForValue()
            .set(compoundKeyWithKeyspace(getKeyFromEntity(value)), value, ttl)
    }

    /**
     * Save key to hold the string value if key is absent (SET with NX).
     *
     * @param value the entity to be saved
     * @return a [Mono] emitting `true` if the key did not exist and was set, `false` otherwise
     */
    fun saveIfAbsent(value: V): Mono<Boolean> {
        return reactiveRedisTemplate
            .opsForValue()
            .setIfAbsent(compoundKeyWithKeyspace(getKeyFromEntity(value)), value, this.defaultTTL)
    }

    /**
     * Save key to hold the string value if key is absent (SET with NX).
     *
     * @param value the entity to be saved
     * @param ttl the TTL for the entity to be saved. This parameter will override the default TTL
     *   value
     * @return a [Mono] emitting `true` if the key did not exist and was set, `false` otherwise
     */
    fun saveIfAbsent(value: V, ttl: Duration): Mono<Boolean> {
        return reactiveRedisTemplate
            .opsForValue()
            .setIfAbsent(compoundKeyWithKeyspace(getKeyFromEntity(value)), value, ttl)
    }

    /**
     * Retrieve entity for the given key
     *
     * @param key - the key of the entity to be found
     * @return a [Mono] emitting the value if present; empty if not found
     */
    fun findById(key: String): Mono<V> {
        return reactiveRedisTemplate.opsForValue().get(compoundKeyWithKeyspace(key))
    }

    /**
     * Delete the entity for the given key
     *
     * @param key - the entity key to be deleted
     * @return a [Mono] emitting true if the entity was deleted successfully, false otherwise
     */
    fun deleteById(key: String): Mono<Boolean> {
        return reactiveRedisTemplate.opsForValue().delete(compoundKeyWithKeyspace(key))
    }

    /**
     * Get TTL duration for the entity
     *
     * @param key - the entity key for which retrieve TTL
     * @return a [Mono] emitting the TTL [Duration]; may be [Duration.ZERO] if no expiration is set
     *   or [Mono.empty] if entity does not exist
     * @see org.springframework.data.redis.core.ReactiveRedisOperations.getExpire
     */
    fun getTTL(key: String): Mono<Duration> {
        return reactiveRedisTemplate.getExpire(compoundKeyWithKeyspace(key))
    }

    /**
     * Write an event to the stream with the specified key
     *
     * @param streamKey the stream key where send the event to
     * @param event the event to be sent
     * @return a [Mono] emitting the [RecordId] of the written event
     */
    fun writeEventToStream(streamKey: String, event: V): Mono<RecordId> {
        return reactiveRedisTemplate
            .opsForStream<Any, Any>()
            .add(ObjectRecord.create(streamKey, event))
    }

    /**
     * Write an event to the stream with the specified key trimming events before writing the new
     * events so that stream has the wanted size
     *
     * @param streamKey the stream key where send the event to
     * @param event the event to be sent
     * @param streamSize the wanted length of the stream
     * @return a [Mono] emitting the [RecordId] of the written event
     */
    fun writeEventToStreamTrimmingEvents(
        streamKey: String,
        event: V,
        streamSize: Long
    ): Mono<RecordId> {
        return Mono.just(streamSize)
            .filter { size -> size >= 0 }
            .switchIfEmpty(
                Mono.error(
                    IllegalArgumentException(
                        "Invalid input $streamSize events to trim, it must be >=0")))
            .flatMap { size ->
                reactiveRedisTemplate
                    .opsForStream<Any, Any>()
                    .trim(streamKey, size)
                    .then(
                        reactiveRedisTemplate
                            .opsForStream<Any, Any>()
                            .add(ObjectRecord.create(streamKey, event)))
            }
    }

    /**
     * Trim events from the stream with input key to the wanted size
     *
     * @param streamKey the stream key from which trim events
     * @param streamSize the wanted stream size
     * @return a [Mono] emitting the number of removed entries
     */
    fun trimEvents(streamKey: String, streamSize: Long): Mono<Long> {
        return reactiveRedisTemplate.opsForStream<Any, Any>().trim(streamKey, streamSize)
    }

    /**
     * Acknowledge input record ids for group inside streamKey stream
     *
     * @param streamKey the stream key
     * @param groupName the group id for which perform acknowledgment operation
     * @param recordIds records for which perform ack operation
     * @return a [Mono] emitting the number of acknowledged entries
     */
    fun acknowledgeEvents(
        streamKey: String,
        groupName: String,
        recordIds: Set<String>
    ): Mono<Long> {
        return reactiveRedisTemplate
            .opsForStream<Any, Any>()
            .acknowledge(streamKey, groupName, *recordIds.toTypedArray())
    }

    /**
     * Create a consumer group positioned at the latest event offset for the stream with input id
     *
     * @param streamKey the stream key for which create the group
     * @param groupName the group name
     * @return a [Mono] emitting `"OK"` if the operation succeeded
     */
    fun createGroup(streamKey: String, groupName: String): Mono<Boolean> {
        return reactiveRedisTemplate
            .opsForStream<Any, Any>()
            .createGroup(streamKey, groupName)
            .map { it == "OK" }
    }

    /**
     * Create a consumer group positioned at the latest event offset for the stream with input id
     *
     * @param streamKey the stream key for which create the group
     * @param groupName the group name
     * @param readOffset the offset from which start the receiver group
     * @return a [Mono] emitting `"OK"` if the operation succeeded
     */
    fun createGroup(streamKey: String, groupName: String, readOffset: ReadOffset): Mono<Boolean> {
        return reactiveRedisTemplate
            .opsForStream<Any, Any>()
            .createGroup(streamKey, readOffset, groupName)
            .map { it == "OK" }
    }

    /**
     * Destroy stream consumer group for the stream with input id
     *
     * @param streamKey the stream for which remove the group
     * @param groupName the group name to be destroyed
     * @return a [Mono] emitting `true` if the group was destroyed; `false` otherwise
     */
    fun destroyGroup(streamKey: String, groupName: String): Mono<Boolean> {
        return reactiveRedisTemplate
            .opsForStream<Any, Any>()
            .destroyGroup(streamKey, groupName)
            .map { it == "OK" }
    }

    /**
     * Get all the keys in keyspace
     *
     * @return a [Flux] emitting all keys in the keyspace
     */
    fun keysInKeyspace(): Flux<String> {
        return reactiveRedisTemplate.keys("$keyspace*")
    }

    val allValuesInKeySpace: Flux<V>
        /**
         * Get all the values in keyspace
         *
         * @return a [Flux] emitting all values found in the keyspace
         */
        get() = keysInKeyspace().flatMap(reactiveRedisTemplate.opsForValue()::get)

    /**
     * Unwrap this returning the underling used
     * [org.springframework.data.redis.core.ReactiveRedisTemplate] instance
     *
     * @return this wrapper associated RedisTemplate instance
     */
    fun unwrap(): ReactiveRedisTemplate<String, V> {
        return reactiveRedisTemplate
    }

    /**
     * Get the Redis key from the input entity
     *
     * @param entity - the entity value from which retrieve the Redis key
     * @return the key associated to the input entity
     */
    protected abstract fun getKeyFromEntity(entity: V): String

    private fun compoundKeyWithKeyspace(key: String): String {
        return "$keyspace:$key"
    }
}
