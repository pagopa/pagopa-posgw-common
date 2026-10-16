package it.pagopa.posgw.common.documents.v1.sessions.data

data class AuthOutcomeReceivedData(
    val authDetails: AuthDetails,
    val instrumentDetails: InstrumentDetails
) {
    data class AuthDetails(val outcome: String, val rrn: String, val authorizationCode: String)
}
