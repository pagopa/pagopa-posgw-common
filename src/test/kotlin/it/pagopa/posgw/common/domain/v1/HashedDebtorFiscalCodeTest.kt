package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HashedDebtorFiscalCodeTest {
    @Test
    fun `exposes its value`() {
        assertEquals("obfuscated-code", HashedDebtorFiscalCode("obfuscated-code").value)
    }

    @Test
    fun `rejects a blank value`() {
        assertFailsWith<IllegalArgumentException> { HashedDebtorFiscalCode(" ") }
    }
}
