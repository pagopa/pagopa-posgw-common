package it.pagopa.posgw.common.domain.v1

/** Enum class representing the steps of the process. */
enum class Steps {

    INITIALIZING_SESSION,
    CREATING_DEBT_POSITION,
    ACTIVATING_TRANSACTION,
    REQUESTING_AUTHORIZATION,
    RECEIVING_AUTH_OUTCOME,
}
