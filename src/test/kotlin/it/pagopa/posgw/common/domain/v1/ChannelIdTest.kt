package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ChannelIdTest {
    @Test
    fun `exposes its value`() {
        assertEquals("channel-123", ChannelId("channel-123").value)
    }

    @Test
    fun `requires a nonblank value of at most 35 characters`() {
        assertFailsWith<IllegalArgumentException> { ChannelId(" ") }
        assertEquals("c".repeat(35), ChannelId("c".repeat(35)).value)
        assertFailsWith<IllegalArgumentException> { ChannelId("c".repeat(36)) }
    }
}
