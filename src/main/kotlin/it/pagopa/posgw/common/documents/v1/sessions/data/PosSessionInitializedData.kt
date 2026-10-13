package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.CreditorIstitution
import it.pagopa.posgw.common.domain.v1.Psp

data class PosSessionInitializedData(
    val psp: Psp,
    val creditorInstitution: CreditorIstitution,
    val terminalIds: List<String>,
    val correlationId: String
)
