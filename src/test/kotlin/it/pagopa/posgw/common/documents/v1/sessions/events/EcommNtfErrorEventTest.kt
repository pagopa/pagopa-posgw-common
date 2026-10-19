package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import java.util.UUID
import kotlin.test.Test

class EcommNtfErrorEventTest {
    @Test
    fun `keeps the session id and ecommerce error payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = EcommNtfErrorData("E04", "eCommerce notification failed")

        assertSessionEvent(
            EcommNtfErrorEvent(sessionId, data),
            sessionId,
            SessionEventCode.ECOMM_NTF_ERROR_EVT,
            data,
        )
    }
}
