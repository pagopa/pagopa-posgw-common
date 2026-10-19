package it.pagopa.posgw.common.domain.v1.sessions

import kotlin.test.Test
import kotlin.test.assertEquals

class SessionStatusTest {
    @Test
    fun `contains only the workflow states and exposes matching status values`() {
        val expectedStates =
            listOf(
                "INITIALIZED",
                "DEBT_POSITION_CREATED",
                "TRANSACTION_ACTIVATED",
                "AUTH_REQUESTED",
                "AUTH_REQUEST_ERROR",
                "AUTH_COMPLETED",
                "CI_NTF_ERROR",
                "CI_NTF_EXPIRED",
                "CI_NOTIFIED",
                "RECONCILED",
                "ECOMM_NTF_ERROR",
                "ECOMM_NTF_EXPIRED",
                "ECOMM_NOTIFIED",
                "ERROR",
                "EXPIRED_TO_RECONCILE",
                "EXPIRED",
            )

        assertEquals(expectedStates, SessionStatus.entries.map { it.name })
        assertEquals(expectedStates, SessionStatus.entries.map { it.status })
    }
}
