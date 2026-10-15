package it.pagopa.posgw.common.domain.v1.sessions

enum class SessionEventCode(val code: String) {

    /*
     *  Payment session initialized and list of POS terminal retrieved
     */
    POS_SESSION_INITIALIZED_EVENT("POS_SESSION_INITIALIZED_EVENT"),

    /*
     *  Debt position created
     */
    DEBT_POSITION_CREATED_EVENT("DEBT_POSITION_CREATED_EVENT"),

    /*
     *  Transaction activated on eCommerce
     */
    TRANSACTION_ACTIVATED_EVENT("TRANSACTION_ACTIVATED_EVENT"),

    /*
     *  Authorization requested done on the PSP POS layer
     */
    POS_AUTHORIZATION_REQUESTED_EVENT("POS_AUTHORIZATION_REQUESTED_EVENT"),

    /*
     *  Error occurred during the authorization request on the PSP POS layer
     */
    POS_AUTHORIZATION_REQUEST_ERROR_EVENT("POS_AUTHORIZATION_REQUEST_ERROR_EVENT"),

    /*
     *  Authorization outcome received from the PSP POS layer
     */
    AUTHORIZATION_OUTCOME_RECEIVED_EVENT("AUTHORIZATION_OUTCOME_RECEIVED_EVENT"),

    /*
     *  Authorization outcome notified to the CI
     */
    CI_AUTHORIZATION_OUTCOME_NOTIFIED("CI_AUTHORIZATION_OUTCOME_NOTIFIED"),

    /*
     *  Authorization outcome notified to the CI
     */
    CI_NOTIFIED_EVENT("CI_NOTIFIED_EVENT"),

    /*
     *  Error occurred during the authorization outcome notification to the CI
     */
    CI_NOTIFY_ERROR_EVENT("CI_NOTIFY_ERROR_EVENT"),

    /*
     *  Authorization outcome notified to the eCommerce
     */
    ECOMMERCE_NOTIFIED_EVENT("ECOMMERCE_NOTIFIED_EVENT"),

    /*
     *  Error occurred during the authorization outcome notification to the eCommerce
     */
    ECOMMERCE_NOTIFY_ERROR_EVENT("ECOMMERCE_NOTIFY_ERROR_EVENT"),

    /*
     *  Error occurred
     */
    ERROR_EVENT("ERROR_EVENT"),
    /*
     *  Session expired
     */
    EXPIRED_EVENT("EXPIRED_EVENT")
}
