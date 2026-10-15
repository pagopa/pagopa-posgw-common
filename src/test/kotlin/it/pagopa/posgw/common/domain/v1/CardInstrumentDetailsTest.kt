package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class CardInstrumentDetailsTest {
    @Test
    fun `defaults the instrument type to card`() {
        val details =
            CardInstrumentDetails(
                brand = CardType.MASTERCARD,
                last4Digits = "5678",
                bin = "510510",
            )

        assertEquals(PaymentInstrumentType.CARD, details.type)
        assertEquals(CardType.MASTERCARD, details.brand)
        assertEquals("5678", details.last4Digits)
        assertEquals("510510", details.bin)
    }
}
