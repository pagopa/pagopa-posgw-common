package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AuthorizationCodeTest {
    @Test
    fun `exposes its value`() {
        assertEquals("auth-123", AuthorizationCode("auth-123").value)
    }

    @Test
    fun `rejects a blank value`() {
        assertFailsWith<IllegalArgumentException> { AuthorizationCode(" ") }
    }
}
