package it.pagopa.posgw.common.documents.v1.sessions.events

import kotlin.test.Test
import kotlin.test.assertEquals

class SessionEventCodeTest {
    @Test
    fun `exposes persisted event codes in declaration order`() {
        assertEquals(
            listOf(
                "SESSION_INITIALIZED_EVT",
                "DEBT_POSITION_CREATED_EVT",
                "TRANSACTION_ACTIVATED_EVT",
                "AUTH_REQUESTED_EVT",
                "AUTH_REQUEST_ERROR_EVT",
                "AUTH_REQUEST_RETRIED_EVT",
                "AUTH_OUTCOME_RECEIVED_EVT",
                "CI_NOTIFIED_EVT",
                "CI_NTF_ERROR_EVT",
                "CI_NTF_RETRIED_EVT",
                "CI_NTF_EXPIRED_EVT",
                "ECOMM_NOTIFIED_EVT",
                "ECOMM_NTF_ERROR_EVT",
                "ECOMM_NTF_EXPIRED_EVT",
                "ECOMM_NTF_RETRIED_EVT",
                "ERROR_EVT",
                "EXPIRED_EVT",
                "RECONCILED_EVT",
            ),
            SessionEventCode.entries.map { it.name },
        )
    }
}
