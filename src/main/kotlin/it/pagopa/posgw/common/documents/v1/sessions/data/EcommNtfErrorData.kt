package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Error information from a failed eCommerce notification.
 *
 * @param errorCode code describing the notification error
 * @param errorMessage human-readable description of the notification error
 */
data class EcommNtfErrorData(val errorCode: String, val errorMessage: String)
