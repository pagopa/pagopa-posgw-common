package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRequestErrorDataTest {
    @Test
    fun `keeps the error code and message`() {
        val data = AuthRequestErrorData("E01", "request failed")

        assertEquals("E01", data.errorCode)
        assertEquals("request failed", data.errorMessage)
    }
}
