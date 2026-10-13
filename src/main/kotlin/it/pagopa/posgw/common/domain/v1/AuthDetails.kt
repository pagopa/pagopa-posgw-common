package it.pagopa.posgw.common.domain.v1

/**
 * Data class representing the details of a POS authentication process.
 *
 * @property outcame The outcome of the POS authentication process.
 * @property rrn The Retrieval Reference Number: unique bank transaction identifier.
 * @property authorizationCode The authorization code provided by POS.
 */
data class AuthDetails(
    var outcame: AuthOutcame,
    var rrn: Rrn,
    var authorizationCode: AuthorizationCode
)

/**
 * Enum representing the possible outcomes of a POS authentication process.
 *
 * @property value The string representation of the POS authentication outcome.
 */
enum class AuthOutcame(val value: String) {
    AUTHORIZED("AUTHORIZED"),
    REFUSED("REFUSED"),
    UNAUTHORIZED("UNAUTHORIZED"),
    TIMEOUT("TIMEOUT"),
    CANCELED("CANCELED")
}
