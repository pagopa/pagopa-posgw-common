package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class DebtorFiscalCodeTest {
    @Test
    fun `exposes its value`() {
        assertEquals("obfuscated-code", DebtorFiscalCode("obfuscated-code").value)
    }
}
