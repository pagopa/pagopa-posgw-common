package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a session-level error.
 *
 * @param sessionId identifier of the session
 * @param data error details and status at occurrence
 */
@Document(collection = "eventstore")
data class ErrorEvent(override val sessionId: UUID, override val data: ErrorData) :
    SessionEvent<ErrorData>(
        eventCode = SessionEventCode.ERROR_EVT, data = data, sessionId = sessionId)
