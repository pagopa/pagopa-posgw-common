package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class ExpiredDataTest {
    @Test
    fun `keeps the status at expiration`() {
        val data = ExpiredData("EXPIRED_TO_RECONCILE")

        assertEquals("EXPIRED_TO_RECONCILE", data.atStatus)
    }
}
