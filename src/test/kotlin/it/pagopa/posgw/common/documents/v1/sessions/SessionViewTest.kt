package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPosition
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

class SessionViewTest {
    @Test
    fun `defaults optional session details to empty or null`() {
        val createdAt = Instant.parse("2026-10-19T10:00:00Z")
        val updatedAt = Instant.parse("2026-10-19T10:01:00Z")
        val view =
            SessionView(
                id = "session-1",
                correlationId = "correlation-1",
                status = "INITIALIZED",
                createdAt = createdAt,
                updatedAt = updatedAt,
            )

        assertEquals("session-1", view.id)
        assertEquals("correlation-1", view.correlationId)
        assertEquals("INITIALIZED", view.status)
        assertEquals(createdAt, view.createdAt)
        assertEquals(updatedAt, view.updatedAt)
        assertTrue(view.pspList.isEmpty())
        assertTrue(view.debtPositions.isEmpty())
        assertNull(view.transaction)
        assertNull(view.authorizationRequest)
        assertNull(view.notification)
        assertNull(view.error)
        assertNull(view.authRequestError)
        assertNull(view.ciNtfError)
        assertNull(view.ecommNtfErrorData)
        assertTrue(view.events.isEmpty())
    }

    @Test
    fun `nested psp and notification values expose their fields`() {
        val eCommerceNotifiedAt = Instant.parse("2026-10-19T10:02:00Z")
        val ciNotifiedAt = Instant.parse("2026-10-19T10:03:00Z")
        val psp = SessionView.Psp("psp-1", "broker-1", "channel-1")
        val notification = SessionView.Notification(eCommerceNotifiedAt, ciNotifiedAt)

        assertEquals("psp-1", psp.id)
        assertEquals("broker-1", psp.brokerId)
        assertEquals("channel-1", psp.channelId)
        assertEquals(eCommerceNotifiedAt, notification.eCommerceNotifiedAt)
        assertEquals(ciNotifiedAt, notification.ciNotifiedAt)
    }

    @Test
    fun `keeps populated session details`() {
        val createdAt = Instant.parse("2026-10-19T10:00:00Z")
        val updatedAt = Instant.parse("2026-10-19T10:01:00Z")
        val psps = listOf(SessionView.Psp("psp-1", "broker-1", "channel-1"))
        val positions = listOf(DebtPosition("iupd-1", "rpt-1", "hashed-code"))
        val transaction =
            TransactionActivatedData(
                "transaction-1",
                listOf(
                    TransactionActivatedData.ActivationInfo(
                        "token-1",
                        "rpt-1",
                        100L,
                        "OK",
                        null,
                    ),
                ),
            )
        val authorizationRequest = AuthRequestedData("terminal-1", "authorization-1")
        val notification = SessionView.Notification(updatedAt, createdAt)
        val error = ErrorData("ERROR", "E01", "session failed")
        val authRequestError = AuthRequestErrorData("E02", "authorization failed")
        val ciNtfError = CiNtfErrorData("E03", "CI notification failed")
        val ecommNtfError = EcommNtfErrorData("E04", "eCommerce notification failed")
        val events =
            listOf(CiNotifiedEvent(UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")))
        val view =
            SessionView(
                id = "session-1",
                correlationId = "correlation-1",
                status = "ERROR",
                createdAt = createdAt,
                updatedAt = updatedAt,
                pspList = psps,
                debtPositions = positions,
                transaction = transaction,
                authorizationRequest = authorizationRequest,
                notification = notification,
                error = error,
                authRequestError = authRequestError,
                ciNtfError = ciNtfError,
                ecommNtfErrorData = ecommNtfError,
                events = events,
            )

        assertEquals(psps, view.pspList)
        assertEquals(positions, view.debtPositions)
        assertEquals(transaction, view.transaction)
        assertEquals(authorizationRequest, view.authorizationRequest)
        assertEquals(notification, view.notification)
        assertEquals(error, view.error)
        assertEquals(authRequestError, view.authRequestError)
        assertEquals(ciNtfError, view.ciNtfError)
        assertEquals(ecommNtfError, view.ecommNtfErrorData)
        assertEquals(events, view.events)
    }

    @Test
    fun `maps to the sessions collection and marks its id`() {
        assertEquals("sessions", SessionView::class.java.getAnnotation(Document::class.java).value)
        assertTrue(
            SessionView::class.java.getDeclaredField("id").isAnnotationPresent(Id::class.java))
    }
}
