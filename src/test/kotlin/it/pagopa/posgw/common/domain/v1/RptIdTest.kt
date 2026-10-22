package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class RptIdTest {

    private val validRptId = "32009090901302016723749670035"

    @Test
    fun `exposes its value`() {
        val rptId = RptId(validRptId)
        assertEquals(validRptId, rptId.value)
    }

    @Test
    fun `rejects values that are not exactly 29 digits`() {
        assertFailsWith<IllegalArgumentException> { RptId("1234567890") }
        assertFailsWith<IllegalArgumentException> { RptId("3200909090130201672374967003A") }
    }

    @Test
    fun `exposes segments when no application code is present`() {
        val rptId = RptId(validRptId)

        assertEquals("32009090901", rptId.fiscalCode)
        assertEquals("302016723749670035", rptId.noticeId)
        assertEquals("3", rptId.auxDigit)
        assertNull(rptId.applicationCode)
        assertEquals("02016723749670035", rptId.iuv)
    }

    @Test
    fun `exposes application code and iuv when auxiliary digit is zero`() {
        val rptId = RptId("32009090901012345678901234567")

        assertEquals("32009090901", rptId.fiscalCode)
        assertEquals("012345678901234567", rptId.noticeId)
        assertEquals("0", rptId.auxDigit)
        assertEquals("12", rptId.applicationCode)
        assertEquals("345678901234567", rptId.iuv)
    }
}
