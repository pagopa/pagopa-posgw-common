package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class AuthOutcomeTest {
    @Test
    fun `maps authorization outcomes to their external values`() {
        assertEquals(
            listOf("AUTHORIZED", "REFUSED", "UNAUTHORIZED", "TIMEOUT", "CANCELED"),
            AuthOutcome.entries.map { it.value },
        )
    }
}
