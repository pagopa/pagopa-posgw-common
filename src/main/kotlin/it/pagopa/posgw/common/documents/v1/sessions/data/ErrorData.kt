package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus

/**
 * Error details and the session status at which the error occurred.
 *
 * @param atStatus session status when the error occurred
 * @param errorCode code identifying the error
 * @param errorMessage human-readable description of the error
 */
data class ErrorData(val atStatus: SessionStatus, val errorCode: String, val errorMessage: String)
