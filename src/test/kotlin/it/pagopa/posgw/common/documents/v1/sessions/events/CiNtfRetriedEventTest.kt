package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import java.util.UUID
import kotlin.test.Test

class CiNtfRetriedEventTest {
    @Test
    fun `keeps the session id and retry payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = CiNtfRetriedData(2, "E03", "retrying")

        assertSessionEvent(
            CiNtfRetriedEvent(sessionId, data),
            sessionId,
            SessionEventCode.CI_NTF_RETRIED_EVT,
            data,
        )
    }
}
