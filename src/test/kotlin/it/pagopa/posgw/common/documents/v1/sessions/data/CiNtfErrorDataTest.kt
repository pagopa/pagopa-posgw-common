package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class CiNtfErrorDataTest {
    @Test
    fun `keeps the error code and message`() {
        val data = CiNtfErrorData("E03", "CI notification failed")

        assertEquals("E03", data.errorCode)
        assertEquals("CI notification failed", data.errorMessage)
    }
}
