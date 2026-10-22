package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRequestedDataTest {
    @Test
    fun `keeps terminal and authorization identifiers`() {
        val data = AuthRequestedData("terminal-1", "authorization-1")

        assertEquals("terminal-1", data.terminalId)
        assertEquals("authorization-1", data.authorizationId)
    }
}
