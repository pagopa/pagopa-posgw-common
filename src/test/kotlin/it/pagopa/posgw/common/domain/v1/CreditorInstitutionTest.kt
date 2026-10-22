package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreditorInstitutionTest {
    @Test
    fun `keeps its identity and associated details`() {
        val institution =
            CreditorInstitution(
                fiscalCode = CreditorInstitution.FiscalCode("fiscal-code"),
                logicalOffice = CreditorInstitution.LogicalOffice("office-123"),
                workStation = CreditorInstitution.WorkStation("workstation-123"),
            )

        assertEquals(CreditorInstitution.FiscalCode("fiscal-code"), institution.fiscalCode)
        assertEquals(CreditorInstitution.LogicalOffice("office-123"), institution.logicalOffice)
        assertEquals(CreditorInstitution.WorkStation("workstation-123"), institution.workStation)
    }

    @Test
    fun `rejects blank nested identifiers`() {
        assertFailsWith<IllegalArgumentException> { CreditorInstitution.FiscalCode(" ") }
        assertFailsWith<IllegalArgumentException> { CreditorInstitution.LogicalOffice(" ") }
        assertFailsWith<IllegalArgumentException> { CreditorInstitution.WorkStation(" ") }
    }
}
