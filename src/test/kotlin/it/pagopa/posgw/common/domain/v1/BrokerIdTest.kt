package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class BrokerIdTest {
    @Test
    fun `exposes its value`() {
        assertEquals("broker-123", BrokerId("broker-123").value)
    }
}
