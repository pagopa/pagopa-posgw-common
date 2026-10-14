package it.pagopa.posgw.common.domain.v1

/**
 * Enum class representing the steps of the process.
 *
 * @property value The string representation of the step.
 */
enum class Steps(val value: String) {

    INITIALIZING_SESSION("INITIALIZING_SESSION"),
    CREATING_DEBT_POSITION("CREATING_DEBT_POSITION"),
    ACTIVATING_TRANSACTION("ACTIVATING_TRANSACTION"),
    REQUESTING_AUTHORIZATION("REQUESTING_AUTHORIZATION"),
    RECEIVING_AUTH_OUTCOME("RECEIVING_AUTH_OUTCOME"),
}
