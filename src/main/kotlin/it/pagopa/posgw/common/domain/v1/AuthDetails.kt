package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Data class representing the details of a POS authentication process.
 *
 * @property outcome The outcome of the POS authentication process.
 * @property rrn The Retrieval Reference Number: unique bank transaction identifier.
 * @property authorizationCode The authorization code provided by POS.
 */
@ValueObject
data class AuthDetails(
    val outcome: AuthOutcome,
    val rrn: Rrn,
    val authorizationCode: AuthorizationCode
)

/**
 * Enum representing the possible outcomes of a POS authentication process.
 *
 * @property value The string representation of the POS authentication outcome.
 */
enum class AuthOutcome(val value: String) {
    AUTHORIZED("AUTHORIZED"),
    REFUSED("REFUSED"),
    UNAUTHORIZED("UNAUTHORIZED"),
    TIMEOUT("TIMEOUT"),
    CANCELED("CANCELED")
}
