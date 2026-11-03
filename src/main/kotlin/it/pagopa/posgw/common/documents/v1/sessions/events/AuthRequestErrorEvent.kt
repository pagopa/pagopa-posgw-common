package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records an authorization request failure.
 *
 * @param sessionId identifier of the session
 * @param data authorization request error details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class AuthRequestErrorEvent(
    sessionId: UUID,
    data: AuthRequestErrorData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<AuthRequestErrorData>(
        sessionId, SessionEventCode.AUTH_REQUEST_ERROR_EVT, data, id, timestamp)
