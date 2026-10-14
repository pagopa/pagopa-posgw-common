package it.pagopa.posgw.common.domain.v1

import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RptIdTest {

    // 29 digits is a valid RPT identifier.
    private val validRptId = "32009090901302016723749670035"
    private val invalidRptId = "1234567890"

    @Test
    fun shouldValidateRptId() {
        val rptId = RptId(validRptId)
        Assertions.assertEquals(validRptId, rptId.value)
    }

    @Test
    fun shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            RptId(invalidRptId)
        }
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
