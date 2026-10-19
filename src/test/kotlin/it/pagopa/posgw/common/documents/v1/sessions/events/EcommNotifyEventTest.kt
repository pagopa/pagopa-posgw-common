package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import kotlin.test.Test

class EcommNotifyEventTest {
    @Test
    fun `uses the ecommerce notified code and unit payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")

        assertSessionEvent(
            EcommNotifyEvent(sessionId),
            sessionId,
            SessionEventCode.ECOMM_NOTIFIED_EVT,
            Unit,
        )
    }
}
