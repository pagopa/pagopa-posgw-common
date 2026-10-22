package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BrokerIdTest {
    @Test
    fun `exposes its value`() {
        assertEquals("broker-123", BrokerId("broker-123").value)
    }

    @Test
    fun `requires a nonblank value of at most 35 characters`() {
        assertFailsWith<IllegalArgumentException> { BrokerId(" ") }
        assertEquals("b".repeat(35), BrokerId("b".repeat(35)).value)
        assertFailsWith<IllegalArgumentException> { BrokerId("b".repeat(36)) }
    }
}
