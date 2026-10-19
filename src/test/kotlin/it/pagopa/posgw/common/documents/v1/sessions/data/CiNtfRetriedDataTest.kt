package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class CiNtfRetriedDataTest {
    @Test
    fun `keeps retry details`() {
        val data = CiNtfRetriedData(3, "E04", "retrying CI notification")

        assertEquals(3, data.retryCount)
        assertEquals("E04", data.errorCode)
        assertEquals("retrying CI notification", data.errorMessage)
    }
}
