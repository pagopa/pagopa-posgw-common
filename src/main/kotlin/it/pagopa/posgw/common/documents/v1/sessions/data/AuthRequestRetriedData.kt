package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Error and attempt information recorded when an authorization request is retried.
 *
 * @param errorCode code associated with the request error
 * @param errorMessage description associated with the request error
 * @param retryCount number of retries performed
 */
data class AuthRequestRetriedData(
    val errorCode: String,
    val errorMessage: String,
    val retryCount: Int
)
