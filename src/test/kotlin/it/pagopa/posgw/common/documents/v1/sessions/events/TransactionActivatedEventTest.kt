package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.util.UUID
import kotlin.test.Test

class TransactionActivatedEventTest {
    @Test
    fun `keeps the session id and transaction payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data =
            TransactionActivatedData(
                "transaction-1",
                listOf(
                    TransactionActivatedData.ActivationInfo("token-1", "rpt-1", 100L, "OK", null)),
            )

        assertSessionEvent(
            TransactionActivatedEvent(sessionId, data),
            sessionId,
            SessionEventCode.TRANSACTION_ACTIVATED_EVT,
            data,
        )
    }
}
