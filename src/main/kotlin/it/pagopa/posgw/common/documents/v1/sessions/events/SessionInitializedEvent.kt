package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.SessionInitializedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records initialization of a payment session.
 *
 * @param sessionId identifier assigned to the session
 * @param data session initialization details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class SessionInitializedEvent(
    sessionId: UUID,
    data: SessionInitializedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<SessionInitializedData>(
        sessionId, SessionEventCode.SESSION_INITIALIZED_EVT, data, id, timestamp)
