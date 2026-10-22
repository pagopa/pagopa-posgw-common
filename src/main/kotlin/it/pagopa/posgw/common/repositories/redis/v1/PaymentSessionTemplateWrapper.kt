package it.pagopa.posgw.common.repositories.redis.v1

import it.pagopa.posgw.common.repositories.redis.ReactiveRedisTemplateWrapper
import org.springframework.data.redis.core.ReactiveRedisTemplate

/**
 * Stores [PaymentSession] values in the `session` Redis keyspace, using each session ID as its key.
 *
 * @param reactiveRedisTemplate template used to read and write payment sessions
 * @param ttl default expiration applied when saving payment sessions
 */
class PaymentSessionTemplateWrapper(
    reactiveRedisTemplate: ReactiveRedisTemplate<String, PaymentSession>,
    ttl: java.time.Duration
) :
    ReactiveRedisTemplateWrapper<PaymentSession>(
        reactiveRedisTemplate = reactiveRedisTemplate, keyspace = "session", ttl = ttl) {
    /**
     * Returns the session ID used as the Redis key.
     *
     * @param entity payment session whose key is extracted
     * @return the session ID
     */
    override fun getKeyFromEntity(entity: PaymentSession): String = entity.sessionId
}
