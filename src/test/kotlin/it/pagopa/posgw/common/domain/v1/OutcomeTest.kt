package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class OutcomeTest {
    @Test
    fun `maps activation outcomes to their external values`() {
        assertEquals(listOf("OK", "KO"), Outcome.entries.map { it.value })
    }
}
