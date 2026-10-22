package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import kotlin.test.Test

class CiNotifiedEventTest {
    @Test
    fun `uses the CI notified code and unit payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")

        assertSessionEvent(
            CiNotifiedEvent(sessionId),
            sessionId,
            SessionEventCode.CI_NOTIFIED_EVT,
            Unit,
        )
    }
}
