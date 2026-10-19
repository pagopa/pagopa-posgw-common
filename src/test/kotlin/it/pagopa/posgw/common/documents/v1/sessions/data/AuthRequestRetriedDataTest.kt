package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRequestRetriedDataTest {
    @Test
    fun `keeps retry details`() {
        val data = AuthRequestRetriedData("E02", "retrying authorization", 2)

        assertEquals("E02", data.errorCode)
        assertEquals("retrying authorization", data.errorMessage)
        assertEquals(2, data.retryCount)
    }
}
