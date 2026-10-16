package it.pagopa.posgw.common.documents.v1.sessions.events

enum class SessionEventCode {

    /*
     *  Payment session initialized and list of POS terminal retrieved
     */
    SESSION_INITIALIZED_EVT,

    /*
     *  Debt position created
     */
    DEBT_POSITION_CREATED_EVT,

    /*
     *  Transaction activated on eCommerce
     */
    TRANSACTION_ACTIVATED_EVT,

    /*
     *  Authorization requested done on the PSP POS layer
     */
    AUTH_REQUESTED_EVT,

    /*
     *  Error occurred during the authorization request on the PSP POS layer
     */
    AUTH_REQUEST_ERROR_EVT,

    /*
     *  Authorization request retried on the PSP POS layer
     */
    AUTH_REQUEST_RETRIED_EVT,

    /*
     *  Authorization outcome received from the PSP POS layer
     */
    AUTH_OUTCOME_RECEIVED_EVT,

    /*
     *  Authorization outcome notified to the CI
     */
    CI_NOTIFIED_EVT,

    /*
     *  Authorization outcome notification to the CI failed
     */
    CI_NTF_ERROR_EVT,

    /*
     *  Authorization outcome notification to the CI retried
     */
    CI_NTF_RETRIED_EVT,

    /*
     *  Authorization outcome notification to the CI expired
     */
    CI_NTF_EXPIRED_EVT,

    /*
     *  Authorization outcome notified to the eCommerce
     */
    ECOMM_NOTIFIED_EVT,

    /*
     *  Error occurred during the authorization outcome notification to the eCommerce
     */
    ECOMM_NTF_ERROR_EVT,

    /*
     *  Authorization outcome notification to the eCommerce expired
     */
    ECOMM_NTF_EXPIRED_EVT,

    /*
     *  Error occurred
     */
    ERROR_EVT,

    /*
     *  Session expired
     */
    EXPIRED_EVT,

    /*
     *  Session reconciled
     */
    RECONCILED_EVT
}