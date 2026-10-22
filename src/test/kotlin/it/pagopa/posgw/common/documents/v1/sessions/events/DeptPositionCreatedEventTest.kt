package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPosition
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import java.util.UUID
import kotlin.test.Test

class DeptPositionCreatedEventTest {
    @Test
    fun `keeps the session id and created debt positions`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data = DebtPositionCreatedData(listOf(DebtPosition("iupd-1", "rpt-1", "hashed-code")))

        assertSessionEvent(
            DeptPositionCreatedEvent(sessionId, data),
            sessionId,
            SessionEventCode.DEBT_POSITION_CREATED_EVT,
            data,
        )
    }
}
