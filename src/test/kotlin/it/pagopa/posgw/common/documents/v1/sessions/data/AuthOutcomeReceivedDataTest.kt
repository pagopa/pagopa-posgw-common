package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class AuthOutcomeReceivedDataTest {
    @Test
    fun `keeps authorization and instrument details`() {
        val authDetails = AuthOutcomeReceivedData.AuthDetails("AUTHORIZED", "rrn-123", "auth-123")
        val instrumentDetails = CardDetails("VISA", "1234", "411111")
        val data = AuthOutcomeReceivedData(authDetails, instrumentDetails)

        assertEquals("AUTHORIZED", data.authDetails.outcome)
        assertEquals("rrn-123", data.authDetails.rrn)
        assertEquals("auth-123", data.authDetails.authorizationCode)
        assertEquals(instrumentDetails, data.instrumentDetails)
    }
}
