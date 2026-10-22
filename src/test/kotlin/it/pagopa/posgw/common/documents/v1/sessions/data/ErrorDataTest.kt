package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class ErrorDataTest {
    @Test
    fun `keeps status and error details`() {
        val data = ErrorData("ERROR", "E07", "session failed")

        assertEquals("ERROR", data.atStatus)
        assertEquals("E07", data.errorCode)
        assertEquals("session failed", data.errorMessage)
    }
}
