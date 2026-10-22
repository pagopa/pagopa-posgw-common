package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import kotlin.test.Test

class ReconciledEventTest {
    @Test
    fun `uses the reconciled code and unit payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")

        assertSessionEvent(
            ReconciledEvent(sessionId),
            sessionId,
            SessionEventCode.RECONCILED_EVT,
            Unit,
        )
    }
}
