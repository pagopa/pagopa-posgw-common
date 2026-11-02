package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a retry of a failed authorization request.
 *
 * @param sessionId identifier of the session
 * @param data retry count and error details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class AuthRequestRetriedEvent(
    sessionId: UUID,
    data: AuthRequestRetriedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<AuthRequestRetriedData>(
        sessionId, SessionEventCode.AUTH_REQUEST_RETRIED_EVT, data, id, timestamp)
