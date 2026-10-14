package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class ChannelIdTest {
    @Test
    fun `exposes its value`() {
        assertEquals("channel-123", ChannelId("channel-123").value)
    }
}
