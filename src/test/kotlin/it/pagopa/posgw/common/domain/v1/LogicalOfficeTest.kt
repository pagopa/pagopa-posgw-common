package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class LogicalOfficeTest {
    @Test
    fun `uses value equality`() {
        assertEquals(LogicalOffice("office-123"), LogicalOffice("office-123"))
        assertNotEquals(LogicalOffice("office-123"), LogicalOffice("office-456"))
    }
}
