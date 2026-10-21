package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import java.util.UUID
import kotlin.test.Test

class ExpiredEventTest {
    @Test
    fun `keeps the session id and expiration payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = ExpiredData("EXPIRED")

        assertSessionEvent(
            ExpiredEvent(sessionId, data),
            sessionId,
            SessionEventCode.EXPIRED_EVT,
            data,
        )
    }
}
