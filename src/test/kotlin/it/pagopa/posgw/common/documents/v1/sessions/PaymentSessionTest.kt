package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class PaymentSessionTest {
    @Test
    fun `keeps top-level and nested session values`() {
        val createdAt = Instant.parse("2026-10-20T10:00:00Z")
        val activationTimeoutAt = Instant.parse("2026-10-20T10:01:00Z")
        val authorizationTimeoutAt = Instant.parse("2026-10-20T10:02:00Z")
        val paymentSession =
            PaymentSession(
                sessionId = "session-1",
                status = SessionStatus.TRANSACTION_ACTIVATED,
                correlationId = "correlation-1",
                integrationType = PaymentSession.IntegrationType.STANDARD,
                pos = PaymentSession.Pos("terminal-1"),
                transaction = PaymentSession.Transaction("transaction-1", 1200L),
                lifecycle =
                    PaymentSession.LifeCycle(
                        createdAt, activationTimeoutAt, authorizationTimeoutAt),
                routing =
                    PaymentSession.Rounting("https://ci.example/notify", "https://pos.example"),
            )

        assertEquals("session-1", paymentSession.sessionId)
        assertEquals(SessionStatus.TRANSACTION_ACTIVATED, paymentSession.status)
        assertEquals("correlation-1", paymentSession.correlationId)
        assertEquals(PaymentSession.IntegrationType.STANDARD, paymentSession.integrationType)
        assertEquals("terminal-1", paymentSession.pos.terminalId)
        assertEquals("transaction-1", paymentSession.transaction.id)
        assertEquals(1200L, paymentSession.transaction.amount)
        assertEquals(createdAt, paymentSession.lifecycle.createdAt)
        assertEquals(activationTimeoutAt, paymentSession.lifecycle.activationTimeoutAt)
        assertEquals(authorizationTimeoutAt, paymentSession.lifecycle.authorizationTimeoutAt)
        assertEquals("https://ci.example/notify", paymentSession.routing.ciNotificationUrl)
        assertEquals("https://pos.example", paymentSession.routing.layerPosPSPUrl)
    }

    @Test
    fun `supports data class equality hashing and copying`() {
        val paymentSession =
            PaymentSession(
                sessionId = "session-1",
                status = SessionStatus.INITIALIZED,
                correlationId = "correlation-1",
                integrationType = PaymentSession.IntegrationType.LEGACY,
                pos = PaymentSession.Pos("terminal-1"),
                transaction = PaymentSession.Transaction("transaction-1", 1200L),
                lifecycle =
                    PaymentSession.LifeCycle(
                        Instant.parse("2026-10-20T10:00:00Z"),
                        Instant.parse("2026-10-20T10:01:00Z"),
                        Instant.parse("2026-10-20T10:02:00Z"),
                    ),
                routing =
                    PaymentSession.Rounting("https://ci.example/notify", "https://pos.example"),
            )
        val identicalCopy = paymentSession.copy()

        assertEquals(paymentSession, identicalCopy)
        assertEquals(paymentSession.hashCode(), identicalCopy.hashCode())
        assertNotEquals(paymentSession, paymentSession.copy(correlationId = "correlation-2"))
    }
}
