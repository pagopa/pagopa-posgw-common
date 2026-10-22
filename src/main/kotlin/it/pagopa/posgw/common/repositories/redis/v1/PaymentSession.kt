package it.pagopa.posgw.common.repositories.redis.v1

import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

/**
 * Represents a payment session used to track the transient and reactive state of a payment session
 * managed by the POS Gateway.
 *
 * @param sessionId unique identifier for the payment session
 * @param status current status of the payment session
 * @param correlationId identifier used for correlating related events or requests
 * @param integrationType type of integration used for the payment session
 * @param pos information about the POS terminal involved in the session
 * @param transaction details of the transaction associated with the session
 * @param lifecycle timestamps related to the lifecycle of the session
 * @param routing URLs used for routing notifications and requests related to the session
 */
@RedisHash(value = "session")
data class PaymentSession(
    @Id val sessionId: String,
    val status: String,
    val correlationId: String,
    val integrationType: IntegrationType,
    val pos: Pos,
    val transaction: Transaction,
    val lifecycle: LifeCycle,
    val routing: Routing
) {

    enum class IntegrationType {
        LEGACY,
        STANDARD
    }

    @JvmInline value class Pos(val terminalId: String)

    data class Transaction(val id: String, val amount: Long)

    data class LifeCycle(
        val createdAt: Instant,
        val activationTimeoutAt: Instant,
        val authorizationTimeoutAt: Instant
    )

    data class Routing(val ciNotificationUrl: String, val layerPosPSPUrl: String)
}
