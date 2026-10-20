package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant

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
data class PaymentSession(
    val sessionId: String,
    val status: SessionStatus,
    val correlationId: String,
    val integrationType: IntegrationType,
    val pos: Pos,
    val transaction: Transaction,
    val lifecycle: LifeCycle,
    val routing: Rounting
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

    data class Rounting(val ciNotificationUrl: String, val layerPosPSPUrl: String)
}
