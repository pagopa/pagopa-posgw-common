package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class PaymentTokenTest {
    @Test
    fun `exposes its value`() {
        assertEquals("payment-token-123", PaymentToken("payment-token-123").value)
    }
}
