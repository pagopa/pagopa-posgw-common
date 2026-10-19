package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import it.pagopa.posgw.common.documents.v1.sessions.data.CardDetails
import java.util.UUID
import kotlin.test.Test

class AuthOutcomeReceivedEventTest {
    @Test
    fun `keeps the session id and authorization outcome payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data =
            AuthOutcomeReceivedData(
                AuthOutcomeReceivedData.AuthDetails("AUTHORIZED", "rrn-1", "auth-1"),
                CardDetails("VISA", "1234", "411111"),
            )

        assertSessionEvent(
            AuthOutcomeReceivedEvent(sessionId, data),
            sessionId,
            SessionEventCode.AUTH_OUTCOME_RECEIVED_EVT,
            data,
        )
    }
}
