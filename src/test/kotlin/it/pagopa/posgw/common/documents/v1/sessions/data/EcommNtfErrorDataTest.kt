package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class EcommNtfErrorDataTest {
    @Test
    fun `keeps the error code and message`() {
        val data = EcommNtfErrorData("E05", "eCommerce notification failed")

        assertEquals("E05", data.errorCode)
        assertEquals("eCommerce notification failed", data.errorMessage)
    }
}
