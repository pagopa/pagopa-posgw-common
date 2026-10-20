package it.pagopa.posgw.common.domain.v1.sessions

/** Lifecycle statuses used to identify the current state of a session. */
enum class SessionStatus {
    INITIALIZED,
    DEBT_POSITION_CREATED,
    TRANSACTION_ACTIVATED,
    AUTH_REQUESTED,
    AUTH_REQUEST_ERROR,
    AUTH_COMPLETED,
    CI_NTF_ERROR,
    CI_NTF_EXPIRED,
    CI_NOTIFIED,
    RECONCILED,
    ECOMM_NTF_ERROR,
    ECOMM_NTF_EXPIRED,
    ECOMM_NOTIFIED,
    ERROR,
    EXPIRED_TO_RECONCILE,
    EXPIRED;

    /** The enum entry name, suitable for status serialization. */
    val status: String
        get() = name
}
