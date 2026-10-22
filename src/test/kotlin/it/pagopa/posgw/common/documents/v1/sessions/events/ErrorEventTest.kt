package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import java.util.UUID
import kotlin.test.Test

class ErrorEventTest {
    @Test
    fun `keeps the session id and error payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = ErrorData("ERROR", "E06", "session failed")

        assertSessionEvent(
            ErrorEvent(sessionId, data),
            sessionId,
            SessionEventCode.ERROR_EVT,
            data,
        )
    }
}
