package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardInstrumentDetailsTest {
    @Test
    fun `defaults the instrument type to card`() {
        val details =
            InstrumentDetails.CardInstrumentDetails(
                brand = "MASTERCARD",
                last4Digits = "5678",
                bin = "510510",
            )

        assertEquals(InstrumentDetails.Type.CARD, details.type)
        assertEquals("MASTERCARD", details.brand)
        assertEquals("5678", details.last4Digits)
        assertEquals("510510", details.bin)
    }

    @Test
    fun `requires exactly four numeric last digits`() {
        listOf("123", "12345", "12A4").forEach { last4Digits ->
            assertFailsWith<IllegalArgumentException> {
                InstrumentDetails.CardInstrumentDetails("VISA", last4Digits, "411111")
            }
        }
    }

    @Test
    fun `requires a numeric bin between six and eight digits`() {
        listOf("12345", "123456789", "12345A").forEach { bin ->
            assertFailsWith<IllegalArgumentException> {
                InstrumentDetails.CardInstrumentDetails("VISA", "1234", bin)
            }
        }

        assertEquals(
            "123456", InstrumentDetails.CardInstrumentDetails("VISA", "1234", "123456").bin)
        assertEquals(
            "12345678", InstrumentDetails.CardInstrumentDetails("VISA", "1234", "12345678").bin)
    }
}
