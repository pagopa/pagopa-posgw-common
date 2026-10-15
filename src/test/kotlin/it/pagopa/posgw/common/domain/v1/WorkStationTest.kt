package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class WorkStationTest {
    @Test
    fun `exposes its value`() {
        assertEquals("workstation-123", WorkStation("workstation-123").value)
    }
}
