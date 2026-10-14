package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class CardTypeTest {
    @Test
    fun `maps card brands to their external values`() {
        assertEquals(
            listOf("VISA", "MASTERCARD", "DINERS", "AMEX", "MAESTRO"),
            CardType.entries.map { it.value },
        )
    }
}
