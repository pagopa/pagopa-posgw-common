package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import java.util.UUID
import kotlin.test.Test

class AuthRequestedEventTest {
    @Test
    fun `keeps the session id and authorization request payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = AuthRequestedData("terminal-1", "authorization-1")

        assertSessionEvent(
            AuthRequestedEvent(sessionId, data),
            sessionId,
            SessionEventCode.AUTH_REQUESTED_EVT,
            data,
        )
    }
}
