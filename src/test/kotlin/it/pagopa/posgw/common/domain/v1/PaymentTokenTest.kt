package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PaymentTokenTest {
    @Test
    fun `exposes its value`() {
        assertEquals("payment-token-123", PaymentToken("payment-token-123").value)
    }

    @Test
    fun `requires a nonblank value of at most 35 characters`() {
        assertFailsWith<IllegalArgumentException> { PaymentToken(" ") }
        assertEquals("p".repeat(35), PaymentToken("p".repeat(35)).value)
        assertFailsWith<IllegalArgumentException> { PaymentToken("p".repeat(36)) }
    }
}
