package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records an authorization request for a session.
 *
 * @param sessionId identifier of the session
 * @param data authorization request identifiers
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class AuthRequestedEvent(
    sessionId: UUID,
    data: AuthRequestedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<AuthRequestedData>(
        sessionId, SessionEventCode.AUTH_REQUESTED_EVT, data, id, timestamp)
