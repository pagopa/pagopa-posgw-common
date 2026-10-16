package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus

data class ErrorData(val atStatus: SessionStatus, val errorCode: String, val errorMessage: String)
