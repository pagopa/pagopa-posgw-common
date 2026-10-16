package it.pagopa.posgw.common.documents.v1.sessions.data

data class CiNtfRetriedData(
    val retryCount: Int,
    val errorCode: String,
    val errorMessage: String
)
