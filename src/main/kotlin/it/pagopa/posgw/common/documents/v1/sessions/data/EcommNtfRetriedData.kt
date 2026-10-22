package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Retry count and error details for an eCommerce notification.
 *
 * @param retryCount number of notification retries performed
 * @param errorCode code associated with the notification error
 * @param errorMessage description associated with the notification error
 */
data class EcommNtfRetriedData(val retryCount: Int, val errorCode: String, val errorMessage: String)
