package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals

class DebtPositionCreatedDataTest {
    @Test
    fun `keeps all created debt positions`() {
        val first = DebtPosition("iupd-1", "rpt-1", "hashed-1")
        val second = DebtPosition("iupd-2", "rpt-2", "hashed-2")
        val data = DebtPositionCreatedData(listOf(first, second))

        assertEquals(listOf(first, second), data.depPositions)
    }
}
