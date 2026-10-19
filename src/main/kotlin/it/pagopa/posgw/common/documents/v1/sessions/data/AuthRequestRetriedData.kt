package it.pagopa.posgw.common.documents.v1.sessions.data

data class AuthRequestRetriedData(
    val errorCode: String,
    val errorMessage: String,
    val retryCount: Int
)
