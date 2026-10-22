package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class CorrelationIdTest {
    @Test
    fun `generates a version four uuid when omitted`() {
        val generatedId = CorrelationId().value

        assertEquals(4, generatedId.version())
        assertEquals(2, generatedId.variant())
    }
}
