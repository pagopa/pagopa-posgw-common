package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class DebtPositionTest {
    @Test
    fun `keeps identifiers and debtor fiscal code`() {
        val rptId = RptId("32009090901302016723749670035")
        val debtPosition =
            DebtPosition(
                iupd = Iupd("iupd-123"),
                rptId = rptId,
                debtorFiscalCode = DebtorFiscalCode("obfuscated-code"),
            )

        assertEquals(Iupd("iupd-123"), debtPosition.iupd)
        assertEquals(rptId, debtPosition.rptId)
        assertEquals(DebtorFiscalCode("obfuscated-code"), debtPosition.debtorFiscalCode)
    }
}
