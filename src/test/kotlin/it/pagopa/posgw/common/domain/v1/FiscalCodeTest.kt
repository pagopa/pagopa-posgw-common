package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class FiscalCodeTest {
    @Test
    fun `exposes its value`() {
        assertEquals("fiscal-code", FiscalCode("fiscal-code").value)
    }
}
