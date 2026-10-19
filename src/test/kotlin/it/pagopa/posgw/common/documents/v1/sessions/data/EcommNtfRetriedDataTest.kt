package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class EcommNtfRetriedDataTest {
    @Test
    fun `keeps retry details`() {
        val data = EcommNtfRetriedData(4, "E06", "retrying eCommerce notification")

        assertEquals(4, data.retryCount)
        assertEquals("E06", data.errorCode)
        assertEquals("retrying eCommerce notification", data.errorMessage)
    }
}
