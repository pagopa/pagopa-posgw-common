package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class InstrumentDetailsTest {
    @Test
    fun `exposes card details through the sealed base type`() {
        val details: InstrumentDetails =
            CardInstrumentDetails(
                brand = "VISA",
                last4Digits = "1234",
                bin = "411111",
            )

        val cardDetails = assertIs<CardInstrumentDetails>(details)

        assertEquals(PaymentInstrumentType.CARD, cardDetails.type)
        assertEquals("VISA", cardDetails.brand)
        assertEquals("1234", cardDetails.last4Digits)
        assertEquals("411111", cardDetails.bin)
    }
}
