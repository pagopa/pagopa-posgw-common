package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionActivatedDataTest {
    @Test
    fun `keeps transaction id and activation details`() {
        val activation =
            TransactionActivatedData.ActivationInfo("token-1", "rpt-1", 1200L, "OK", null)
        val data = TransactionActivatedData("transaction-1", listOf(activation))

        assertEquals("transaction-1", data.transactionId)
        assertEquals(listOf(activation), data.activationInfo)
        assertEquals("token-1", data.activationInfo.single().paymentToken)
        assertEquals("rpt-1", data.activationInfo.single().rptId)
        assertEquals(1200L, data.activationInfo.single().amount)
        assertEquals("OK", data.activationInfo.single().outcome)
        assertEquals(null, data.activationInfo.single().reason)
    }
}
