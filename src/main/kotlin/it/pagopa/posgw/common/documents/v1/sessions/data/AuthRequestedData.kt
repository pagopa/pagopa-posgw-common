package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Identifiers sent when requesting authorization for a payment.
 *
 * @param terminalId POS terminal that initiated the authorization request
 * @param authorizationId identifier assigned to the authorization request
 */
data class AuthRequestedData(val terminalId: String, val authorizationId: String)
