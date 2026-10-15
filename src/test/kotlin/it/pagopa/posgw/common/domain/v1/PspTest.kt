package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class PspTest {
    @Test
    fun `allows updating provider configuration`() {
        val psp =
            Psp(
                id = "psp-123",
                brokerId = BrokerId("broker-123"),
                channelId = ChannelId("channel-123"))

        psp.id = "psp-456"
        psp.brokerId = BrokerId("broker-456")
        psp.channelId = ChannelId("channel-456")

        assertEquals("psp-456", psp.id)
        assertEquals(BrokerId("broker-456"), psp.brokerId)
        assertEquals(ChannelId("channel-456"), psp.channelId)
    }
}
