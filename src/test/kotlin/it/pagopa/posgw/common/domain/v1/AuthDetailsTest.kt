package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class AuthDetailsTest {
    @Test
    fun `keeps authorization outcome and references`() {
        val details =
            AuthDetails(
                outcome = AuthOutcome.AUTHORIZED,
                rrn = Rrn("rrn-123"),
                authorizationCode = AuthorizationCode("auth-123"),
            )

        assertEquals(AuthOutcome.AUTHORIZED, details.outcome)
        assertEquals(Rrn("rrn-123"), details.rrn)
        assertEquals(AuthorizationCode("auth-123"), details.authorizationCode)
    }
}
