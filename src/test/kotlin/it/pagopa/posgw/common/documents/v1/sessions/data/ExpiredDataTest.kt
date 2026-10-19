package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ExpiredDataTest {
    @Test
    fun `keeps the status at expiration`() {
        val data = ExpiredData(SessionStatus.EXPIRED_TO_RECONCILE)

        assertEquals(SessionStatus.EXPIRED_TO_RECONCILE, data.atStatus)
    }
}
