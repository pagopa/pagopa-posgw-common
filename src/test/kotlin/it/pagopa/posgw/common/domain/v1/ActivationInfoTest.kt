package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ActivationInfoTest {
    @Test
    fun `keeps activation values and defaults reason to null`() {
        val rptId = RptId("32009090901302016723749670035")
        val activationInfo =
            ActivationInfo(
                paymentToken = PaymentToken("payment-token-123"),
                rptId = rptId,
                amount = 1200L,
                outcome = Outcome.OK,
            )

        assertEquals(PaymentToken("payment-token-123"), activationInfo.paymentToken)
        assertEquals(rptId, activationInfo.rptId)
        assertEquals(1200L, activationInfo.amount)
        assertEquals(Outcome.OK, activationInfo.outcome)
        assertNull(activationInfo.reason)
    }
}
