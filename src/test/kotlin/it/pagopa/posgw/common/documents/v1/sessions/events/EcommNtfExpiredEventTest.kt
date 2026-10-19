package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import kotlin.test.Test

class EcommNtfExpiredEventTest {
    @Test
    fun `uses the ecommerce notification expired code and unit payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")

        assertSessionEvent(
            EcommNtfExpiredEvent(sessionId),
            sessionId,
            SessionEventCode.ECOMM_NTF_EXPIRED_EVT,
            Unit,
        )
    }
}
