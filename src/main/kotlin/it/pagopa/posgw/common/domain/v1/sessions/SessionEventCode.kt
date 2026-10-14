package it.pagopa.posgw.common.domain.v1.sessions

enum class SessionEventCode(val code: String) {

    /*
     *  Payment session initialized and list of POS terminal retrieved
     */
    INITIALIZED("INITIALIZED"),

    /*
     *  Debt position created
     */
    DEBT_POSITION_CREATED("DEBT_POSITION_CREATED"),

    /*
     *  Transaction activated on eCommerce
     */
    TRANSACTION_ACTIVATED("TRANSACTION_ACTIVATED"),

    /*
     *  Authorization requested done on the PSP POS layer
     */
    POS_AUTHORIZATION_REQUESTED("POS_AUTHORIZATION_REQUESTED"),

    /*
     *  Error occurred during the authorization request on the PSP POS layer
     */
    POS_AUTHORIZATION_REQUEST_ERROR("POS_AUTHORIZATION_REQUEST_ERROR"),

    /*
     *  Authorization outcome received from the PSP POS layer
     */
    POS_AUTHORIZED("POS_AUTHORIZED"),

    /*
     *  Authorization outcome notified to the CI
     */
    CI_NOTIFIED("CI_NOTIFIED"),

    /*
     *  Error occurred during the authorization outcome notification to the CI
     */
    CI_NOTIFY_ERROR("CI_NOTIFY_ERROR"),

    /*
     *  Authorization outcome notified to the eCommerce
     */
    ECOMMERCE_NOTIFIED("ECOMMERCE_NOTIFIED"),

    /*
     *  Authorization outcome notified to the eCommerce
     */
    ECOMMERCE_NOTIFY_ERROR("ECOMMERCE_NOTIFY_ERROR"),

    /*
     *  Error occurred
     */
    ERROR("ERROR"),
    /*
     *  Session expired
     */
    EXPIRED("EXPIRED")
}
