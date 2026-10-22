package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class SessionInitializedDataTest {
    @Test
    fun `keeps provider institution terminals and correlation id`() {
        val psp = Psp("psp-1", "broker-1", "channel-1")
        val institution =
            SessionInitializedData.CreditorInstitution("fiscal-code", "office-1", "station-1")
        val data =
            SessionInitializedData(
                psp = psp,
                creditorInstitution = institution,
                terminalIds = listOf("terminal-1", "terminal-2"),
                correlationId = "correlation-1",
            )

        assertEquals(psp, data.psp)
        assertEquals("fiscal-code", data.creditorInstitution.fiscalCode)
        assertEquals("office-1", data.creditorInstitution.logicalOffice)
        assertEquals("station-1", data.creditorInstitution.workStation)
        assertEquals(listOf("terminal-1", "terminal-2"), data.terminalIds)
        assertEquals("correlation-1", data.correlationId)
    }
}
