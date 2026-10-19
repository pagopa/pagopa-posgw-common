package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class AuthRequestRetriedEvent(
    override val sessionId: UUID,
    override val data: AuthRequestRetriedData
) :
    SessionEvent<AuthRequestRetriedData>(
        eventCode = SessionEventCode.AUTH_REQUEST_RETRIED_EVT, data = data, sessionId = sessionId)
