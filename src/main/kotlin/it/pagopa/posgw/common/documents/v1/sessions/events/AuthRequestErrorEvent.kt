package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class AuthRequestErrorEvent(
    override val sessionId: UUID,
    override val data: AuthRequestErrorData
) :
    SessionEvent<AuthRequestErrorData>(
        eventCode = SessionEventCode.AUTH_REQUEST_ERROR_EVT, data = data, sessionId = sessionId)
