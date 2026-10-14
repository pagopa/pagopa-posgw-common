package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class ErrorEvent(override val sessionId: SessionId, override val data: ErrorData) :
    SessionEvent<ErrorData>(
        eventCode = SessionEventCode.ERROR_EVENT, data = data, sessionId = sessionId)
