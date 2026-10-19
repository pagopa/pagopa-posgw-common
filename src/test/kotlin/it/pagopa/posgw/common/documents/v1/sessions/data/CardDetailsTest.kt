package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class CardDetailsTest {
    @Test
    fun `exposes card values and card instrument type`() {
        val details: InstrumentDetails = CardDetails("VISA", "1234", "411111")
        val cardDetails = details as CardDetails

        assertEquals("VISA", cardDetails.brand)
        assertEquals("1234", cardDetails.last4Digits)
        assertEquals("411111", cardDetails.bin)
        assertEquals("CARD", cardDetails.instrumentType)
    }
}
