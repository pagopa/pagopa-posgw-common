package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.Entity

@Entity
data class CreditorInstitution(
    val fiscalCode: FiscalCode,
    val logicalOffice: LogicalOffice,
    val workStation: WorkStation,
)
