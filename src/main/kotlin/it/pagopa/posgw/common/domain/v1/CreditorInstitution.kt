package it.pagopa.posgw.common.domain.v1

data class CreditorInstitution(
    var fiscalCode: FiscalCode,
    var logicalOffice: LogicalOffice,
    var workStation: WorkStation,
)
