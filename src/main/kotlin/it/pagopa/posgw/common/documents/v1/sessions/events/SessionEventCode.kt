package it.pagopa.posgw.common.documents.v1.sessions.events

/** Event codes used to identify session event kinds. */
enum class SessionEventCode {
    /** Session initialized and POS terminal identifiers retrieved. */
    SESSION_INITIALIZED_EVT,
    /** Debt positions created for the session. */
    DEBT_POSITION_CREATED_EVT,
    /** Transaction activated on eCommerce. */
    TRANSACTION_ACTIVATED_EVT,
    /** Authorization requested through the PSP POS layer. */
    AUTH_REQUESTED_EVT,
    /** Authorization request failed on the PSP POS layer. */
    AUTH_REQUEST_ERROR_EVT,
    /** Failed authorization request retried on the PSP POS layer. */
    AUTH_REQUEST_RETRIED_EVT,
    /** Authorization outcome received from the PSP POS layer. */
    AUTH_OUTCOME_RECEIVED_EVT,
    /** Authorization outcome notification sent to the creditor institution. */
    CI_NOTIFIED_EVT,
    /** Authorization outcome notification to the creditor institution failed. */
    CI_NTF_ERROR_EVT,
    /** Failed creditor-institution notification retried. */
    CI_NTF_RETRIED_EVT,
    /** Creditor-institution notification expired. */
    CI_NTF_EXPIRED_EVT,
    /** Authorization outcome notification sent to eCommerce. */
    ECOMM_NOTIFIED_EVT,
    /** Authorization outcome notification to eCommerce failed. */
    ECOMM_NTF_ERROR_EVT,
    /** eCommerce notification expired. */
    ECOMM_NTF_EXPIRED_EVT,
    /** Failed eCommerce notification retried. */
    ECOMM_NTF_RETRIED_EVT,
    /** Session-level error occurred. */
    ERROR_EVT,
    /** Session expired. */
    EXPIRED_EVT,
    /** Expired session reconciled. */
    RECONCILED_EVT
}
