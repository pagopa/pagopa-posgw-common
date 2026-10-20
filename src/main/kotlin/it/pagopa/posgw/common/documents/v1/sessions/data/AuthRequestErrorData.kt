package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Error information returned when an authorization request fails.
 *
 * @param errorCode provider or system code describing the error
 * @param errorMessage human-readable description of the error
 */
data class AuthRequestErrorData(val errorCode: String, val errorMessage: String)
