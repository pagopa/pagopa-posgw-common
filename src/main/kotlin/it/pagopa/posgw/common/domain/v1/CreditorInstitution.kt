package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.Entity

/**
 * A creditor institution, identified by its fiscal code.
 *
 * @property fiscalCode Fiscal code identifying the institution.
 * @property logicalOffice Logical office associated with the institution.
 * @property workStation Workstation associated with the institution.
 */
@Entity
data class CreditorInstitution(
    val fiscalCode: FiscalCode,
    val logicalOffice: LogicalOffice,
    val workStation: WorkStation,
)
