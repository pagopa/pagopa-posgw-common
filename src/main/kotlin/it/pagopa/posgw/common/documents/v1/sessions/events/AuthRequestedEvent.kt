package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records an authorization request for a session.
 *
 * @param sessionId identifier of the session
 * @param data authorization request identifiers
 */
@Document(collection = "eventstore")
data class AuthRequestedEvent(override val sessionId: UUID, override val data: AuthRequestedData) :
    SessionEvent<AuthRequestedData>(
        eventCode = SessionEventCode.AUTH_REQUESTED_EVT, data = data, sessionId = sessionId)
