package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import kotlin.test.Test

class CiNtfErrorEventTest {
    @Test
    fun `uses the CI notification error code and unit payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")

        assertSessionEvent(
            CiNtfErrorEvent(sessionId),
            sessionId,
            SessionEventCode.CI_NTF_ERROR_EVT,
            Unit,
        )
    }
}
