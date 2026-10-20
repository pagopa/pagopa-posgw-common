package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class PaymentInstrumentTypeTest {
    @Test
    fun `maps instrument types to their external values`() {
        assertEquals(listOf("CARD"), PaymentInstrumentType.entries.map { it.name })
    }
}
