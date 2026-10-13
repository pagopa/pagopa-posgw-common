package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.ErrorType
import it.pagopa.posgw.common.domain.v1.Steps
import java.time.Instant

data class ErrorData(
    val step: Steps,
    val timestamp: Instant,
    val errorType: ErrorType,
    val errorCode: String,
    val errorMessage: String
)
