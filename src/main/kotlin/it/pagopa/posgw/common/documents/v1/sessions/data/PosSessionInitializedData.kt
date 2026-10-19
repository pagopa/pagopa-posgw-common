package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.CreditorInstitution
import it.pagopa.posgw.common.domain.v1.Psp

data class PosSessionInitializedData(
    val psp: Psp,
    val creditorInstitution: CreditorInstitution,
    val terminalIds: List<String>,
    val correlationId: String
)
