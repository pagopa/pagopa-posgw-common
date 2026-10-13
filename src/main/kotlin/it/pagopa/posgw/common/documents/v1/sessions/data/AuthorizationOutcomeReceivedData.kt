package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.AuthDetails
import it.pagopa.posgw.common.domain.v1.InstrumentDetails

data class AuthorizationOutcomeReceivedData(
    val authDetails: AuthDetails,
    val instrumentDetails: InstrumentDetails
)
