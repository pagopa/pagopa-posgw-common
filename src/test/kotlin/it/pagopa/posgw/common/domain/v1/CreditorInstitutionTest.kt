package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class CreditorInstitutionTest {
    @Test
    fun `keeps its identity and associated details`() {
        val institution =
            CreditorInstitution(
                fiscalCode = FiscalCode("fiscal-code"),
                logicalOffice = LogicalOffice("office-123"),
                workStation = WorkStation("workstation-123"),
            )

        assertEquals(FiscalCode("fiscal-code"), institution.fiscalCode)
        assertEquals(LogicalOffice("office-123"), institution.logicalOffice)
        assertEquals(WorkStation("workstation-123"), institution.workStation)
    }
}
