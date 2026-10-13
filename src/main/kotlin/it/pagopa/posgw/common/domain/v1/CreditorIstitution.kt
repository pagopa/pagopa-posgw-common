package it.pagopa.posgw.common.domain.v1

data class CreditorIstitution(
    var fiscalCode: FiscalCode,
    var logicalOffice: LogicalOffice,
    var workStation: WorkStation,
)
