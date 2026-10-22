package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RrnTest {
    @Test
    fun `exposes its value`() {
        assertEquals("rrn-123", Rrn("rrn-123").value)
    }

    @Test
    fun `rejects a blank value`() {
        assertFailsWith<IllegalArgumentException> { Rrn(" ") }
    }
}
