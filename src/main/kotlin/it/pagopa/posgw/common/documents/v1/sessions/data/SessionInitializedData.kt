package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.Psp

data class SessionInitializedData(
    val psp: Psp,
    val creditorInstitution: CreditorInstitution,
    val terminalIds: List<String>,
    val correlationId: String
) {
    data class CreditorInstitution(
        val fiscalCode: String,
        val logicalOffice: String,
        val workStation: String,
    )
}
