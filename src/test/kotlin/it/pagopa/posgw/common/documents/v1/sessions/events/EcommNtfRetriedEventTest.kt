package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.util.UUID
import kotlin.test.Test

class EcommNtfRetriedEventTest {
    @Test
    fun `keeps the session id and retry payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = EcommNtfRetriedData(3, "E05", "retrying")

        assertSessionEvent(
            EcommNtfRetriedEvent(sessionId, data),
            sessionId,
            SessionEventCode.ECOMM_NTF_RETRIED_EVT,
            data,
        )
    }
}
