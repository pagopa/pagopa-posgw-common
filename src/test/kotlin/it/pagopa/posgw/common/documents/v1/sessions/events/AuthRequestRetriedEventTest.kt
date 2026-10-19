package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import java.util.UUID
import kotlin.test.Test

class AuthRequestRetriedEventTest {
    @Test
    fun `keeps the session id and retry payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = AuthRequestRetriedData("E02", "retrying", 2)

        assertSessionEvent(
            AuthRequestRetriedEvent(sessionId, data),
            sessionId,
            SessionEventCode.AUTH_REQUEST_RETRIED_EVT,
            data,
        )
    }
}
