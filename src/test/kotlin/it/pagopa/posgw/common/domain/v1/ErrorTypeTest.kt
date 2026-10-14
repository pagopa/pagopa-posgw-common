package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class ErrorTypeTest {
    @Test
    fun `maps error types to their external values`() {
        assertEquals(
            listOf("COMMUNICATION_ERROR", "KO_RESPONSE_RECEIVED"),
            ErrorType.entries.map { it.value },
        )
    }
}
