package it.pagopa.posgw.common.repositories.redis

import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.reactor.mono
import org.mockito.ArgumentMatchers.*
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.internal.verification.VerificationModeFactory.times
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.RecordId
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveStreamOperations
import org.springframework.data.redis.core.ReactiveValueOperations
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class ReactiveRedisTemplateWrapperTest {

    data class MockedEntity(val key: String, val value: String)

    class MockedReactiveRedisTemplateWrapper(
        reactiveRedisTemplate: ReactiveRedisTemplate<String, MockedEntity>,
        keyspace: String,
        ttl: Duration
    ) :
        ReactiveRedisTemplateWrapper<MockedEntity>(
            reactiveRedisTemplate = reactiveRedisTemplate,
            keyspace = keyspace,
            ttl = ttl,
        ) {
        override fun getKeyFromEntity(entity: MockedEntity) = entity.key
    }

    val reactiveRedisTemplateMock: ReactiveRedisTemplate<String, MockedEntity> = mock()

    val opsForValueMock: ReactiveValueOperations<String, MockedEntity> = mock()

    val opsForStreamMock: ReactiveStreamOperations<String, String, MockedEntity> = mock()

    val keyspace = "keyspace"

    val defaultTTl: Duration = Duration.ofSeconds(10)

    val reactiveRedisTemplateTestInstance =
        MockedReactiveRedisTemplateWrapper(
            reactiveRedisTemplate = reactiveRedisTemplateMock,
            keyspace = keyspace,
            ttl = defaultTTl)

    @Test
    fun `Should save entity correctly with default ttl`() {
        // pre-requisites
        val entity = MockedEntity(key = "key", value = "value")
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.set(any(), any(), any<Duration>())).willReturn(mono { true })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.save(entity))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).set("$keyspace:${entity.key}", entity, defaultTTl)
    }

    @Test
    fun `Should save entity correctly with custom ttl`() {
        // pre-requisites
        val entity = MockedEntity(key = "key", value = "value")
        val customTTL = defaultTTl + Duration.ofSeconds(1)
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.set(any(), any(), any<Duration>())).willReturn(mono { true })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.save(entity, customTTL))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).set("$keyspace:${entity.key}", entity, customTTL)
    }

    @Test
    fun `Should save if absent entity correctly with default ttl`() {
        // pre-requisites
        val entity = MockedEntity(key = "key", value = "value")
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.setIfAbsent(any(), any(), any<Duration>())).willReturn(mono { true })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.saveIfAbsent(entity))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).setIfAbsent("$keyspace:${entity.key}", entity, defaultTTl)
    }

    @Test
    fun `Should save if absent entity correctly with custom ttl`() {
        // pre-requisites
        val entity = MockedEntity(key = "key", value = "value")
        val customTTL = defaultTTl + Duration.ofSeconds(1)
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.setIfAbsent(any(), any(), any<Duration>())).willReturn(mono { true })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.saveIfAbsent(entity, customTTL))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).setIfAbsent("$keyspace:${entity.key}", entity, customTTL)
    }

    @Test
    fun `Should find entity by id`() {
        // pre-requisites
        val entity = MockedEntity(key = "key", value = "value")
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.get(any())).willReturn(mono { entity })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.findById(entity.key))
            .expectNext(entity)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).get("$keyspace:${entity.key}")
    }

    @Test
    fun `Should delete by key`() {
        // pre-requisites
        val entityKey = "key1"
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.delete(any())).willReturn(mono { true })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.deleteById(entityKey))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).delete("$keyspace:${entityKey}")
    }

    @Test
    fun `Should return entity ttl`() {
        // pre-requisites
        val entityKey = "key1"
        val entityTTl = Duration.ofSeconds(1)
        given(reactiveRedisTemplateMock.getExpire(any())).willReturn(mono { entityTTl })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.getTTL(entityKey))
            .expectNext(entityTTl)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).getExpire("$keyspace:${entityKey}")
    }

    @Test
    fun `Should write event to stream`() {
        // pre-requisites
        val recordId = RecordId.autoGenerate()
        val entity = MockedEntity(key = "key", value = "value")
        val streamKey = "streamKey"
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.add(any<ObjectRecord<String, MockedEntity>>()))
            .willReturn(mono { recordId })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.writeEventToStream(streamKey, entity))
            .expectNext(recordId)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1)).add(ObjectRecord.create(streamKey, entity))
    }

    @Test
    fun `Should write event to stream trimming previously written events`() {
        // pre-requisites
        val recordId = RecordId.autoGenerate()
        val entity = MockedEntity(key = "key", value = "value")
        val streamKey = "streamKey"
        val streamSize = 1L
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.add(any<ObjectRecord<String, MockedEntity>>()))
            .willReturn(mono { recordId })
        given(opsForStreamMock.trim(any(), anyLong())).willReturn(mono { streamSize })

        // test
        StepVerifier.create(
                reactiveRedisTemplateTestInstance.writeEventToStreamTrimmingEvents(
                    streamKey, entity, streamSize))
            .expectNext(recordId)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(2)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1)).add(ObjectRecord.create(streamKey, entity))
        verify(opsForStreamMock, times(1)).trim(streamKey, streamSize)
    }

    @Test
    fun `Should trim events successfully`() {
        // pre-requisites
        val streamKey = "streamKey"
        val streamSize = 1L
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.trim(any(), anyLong())).willReturn(mono { streamSize })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.trimEvents(streamKey, streamSize))
            .expectNext(streamSize)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1)).trim(streamKey, streamSize)
    }

    @Test
    fun `Should acknowledge events successfully`() {
        // pre-requisites
        val streamKey = "streamKey"
        val groupName = "groupName"
        val recordsToAck = setOf("1", "2", "3")
        val recordsToAckCount = recordsToAck.size.toLong()
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.acknowledge(streamKey, groupName, *recordsToAck.toTypedArray()))
            .willReturn(mono { recordsToAckCount })

        // test
        StepVerifier.create(
                reactiveRedisTemplateTestInstance.acknowledgeEvents(
                    streamKey, groupName, recordsToAck))
            .expectNext(recordsToAckCount)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1))
            .acknowledge(streamKey, groupName, *recordsToAck.toTypedArray())
    }

    @Test
    fun `Should create group successfully`() {
        // pre-requisites
        val streamKey = "streamKey"
        val groupName = "groupName"
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.createGroup(any(), any())).willReturn(mono { "OK" })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.createGroup(streamKey, groupName))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1)).createGroup(streamKey, groupName)
    }

    @Test
    fun `Should create group with read offset successfully`() {
        // pre-requisites
        val streamKey = "streamKey"
        val groupName = "groupName"
        val readOffset = ReadOffset.lastConsumed()
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.createGroup(any(), any(), any())).willReturn(mono { "OK" })

        // test
        StepVerifier.create(
                reactiveRedisTemplateTestInstance.createGroup(streamKey, groupName, readOffset))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1)).createGroup(streamKey, readOffset, groupName)
    }

    @Test
    fun `Should destroy group successfully`() {
        // pre-requisites
        val streamKey = "streamKey"
        val groupName = "groupName"
        given(reactiveRedisTemplateMock.opsForStream<String, MockedEntity>())
            .willReturn(opsForStreamMock)
        given(opsForStreamMock.destroyGroup(any(), any())).willReturn(mono { "OK" })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.destroyGroup(streamKey, groupName))
            .expectNext(true)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).opsForStream<String, MockedEntity>()
        verify(opsForStreamMock, times(1)).destroyGroup(streamKey, groupName)
    }

    @Test
    fun `Should return all keys in keyspace`() {
        // pre-requisites
        val keys = listOf("key1", "key2")
        given(reactiveRedisTemplateMock.keys(any())).willReturn(Flux.fromIterable(keys))

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.keysInKeyspace())
            .expectNextSequence(keys)
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).keys(any())
    }

    @Test
    fun `Should return all values in keyspace`() {
        // pre-requisites
        val keys = listOf("key1", "key2")
        val firstEntity = MockedEntity("key1", "value1")
        val secondEntity = MockedEntity("key2", "value2")
        given(reactiveRedisTemplateMock.keys(any()))
            .willReturn(Flux.fromIterable(keys.map { "$keyspace:$it" }))
        given(reactiveRedisTemplateMock.opsForValue()).willReturn(opsForValueMock)
        given(opsForValueMock.get(eq("$keyspace:key1"))).willReturn(mono { firstEntity })
        given(opsForValueMock.get(eq("$keyspace:key2"))).willReturn(mono { secondEntity })

        // test
        StepVerifier.create(reactiveRedisTemplateTestInstance.allValuesInKeySpace)
            .assertNext { assertEquals(firstEntity, it) }
            .assertNext { assertEquals(secondEntity, it) }
            .verifyComplete()

        // assertions
        verify(reactiveRedisTemplateMock, times(1)).keys(any())
        verify(reactiveRedisTemplateMock, times(1)).opsForValue()
        verify(opsForValueMock, times(1)).get("$keyspace:key1")
        verify(opsForValueMock, times(1)).get("$keyspace:key2")
    }

    @Test
    fun `Should return original redis template instance when unwrapping`() {
        // test
        val unwrappedTemplate = reactiveRedisTemplateTestInstance.unwrap()

        // assertions
        assertEquals(reactiveRedisTemplateMock, unwrappedTemplate)
    }
}
