package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class StepsTest {
    @Test
    fun `maps process steps to their external values`() {
        assertEquals(
            listOf(
                "INITIALIZING_SESSION",
                "CREATING_DEBT_POSITION",
                "ACTIVATING_TRANSACTION",
                "REQUESTING_AUTHORIZATION",
                "RECEIVING_AUTH_OUTCOME"),
            Steps.entries.map { it.value },
        )
    }
}
