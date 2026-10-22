package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class DebtPositionTest {
    @Test
    fun `keeps debt position identifiers`() {
        val data = DebtPosition("iupd-1", "rpt-1", "hashed-code")

        assertEquals("iupd-1", data.iupd)
        assertEquals("rpt-1", data.rptId)
        assertEquals("hashed-code", data.hashedDebtorFiscalCode)
    }
}
