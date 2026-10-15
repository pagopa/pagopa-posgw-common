package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class RrnTest {
    @Test
    fun `exposes its value`() {
        assertEquals("rrn-123", Rrn("rrn-123").value)
    }
}
