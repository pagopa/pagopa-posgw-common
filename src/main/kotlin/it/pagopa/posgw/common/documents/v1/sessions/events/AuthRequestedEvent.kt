package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class AuthRequestedEvent(override val sessionId: UUID, override val data: AuthRequestedData) :
    SessionEvent<AuthRequestedData>(
        eventCode = SessionEventCode.AUTH_REQUESTED_EVT, data = data, sessionId = sessionId)
