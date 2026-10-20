package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Authorization outcome and payment-instrument details returned for a session.
 *
 * @param authDetails authorization result and its network references
 * @param instrumentDetails details identifying the instrument used
 */
data class AuthOutcomeReceivedData(
    val authDetails: AuthDetails,
    val instrumentDetails: InstrumentDetails
) {
    /**
     * Authorization result and references returned by the payment provider.
     *
     * @param outcome authorization outcome
     * @param rrn retrieval reference number
     * @param authorizationCode authorization code returned by the provider
     */
    data class AuthDetails(val outcome: String, val rrn: String, val authorizationCode: String)
}
