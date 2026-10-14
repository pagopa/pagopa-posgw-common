package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class InstrumentDetailsTest {
    @Test
    fun `exposes card details through the sealed base type`() {
        val details: InstrumentDetails =
            CardInstrumentDetails(
                brand = CardType.VISA,
                last4Digits = "1234",
                bin = "411111",
            )

        assertEquals(PaymentInstrumentType.CARD, details.type)
        assertEquals(CardType.VISA, details.brand)
        assertEquals("1234", details.last4Digits)
        assertEquals("411111", details.bin)
    }
}
