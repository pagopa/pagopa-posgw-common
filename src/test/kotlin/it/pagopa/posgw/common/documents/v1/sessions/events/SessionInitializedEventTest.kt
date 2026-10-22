package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.Psp
import it.pagopa.posgw.common.documents.v1.sessions.data.SessionInitializedData
import java.util.UUID
import kotlin.test.Test

class SessionInitializedEventTest {
    @Test
    fun `keeps the session id and initialized session payload`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val data =
            SessionInitializedData(
                Psp("psp-1", "broker-1", "channel-1"),
                SessionInitializedData.CreditorInstitution("fiscal-code", "office", "station"),
                listOf("terminal-1"),
                "correlation-1",
            )

        assertSessionEvent(
            SessionInitializedEvent(sessionId, data),
            sessionId,
            SessionEventCode.SESSION_INITIALIZED_EVT,
            data,
        )
    }
}
