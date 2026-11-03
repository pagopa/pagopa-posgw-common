package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a session-level error.
 *
 * @param sessionId identifier of the session
 * @param data error details and status at occurrence
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class ErrorEvent(
    sessionId: UUID,
    data: ErrorData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) : SessionEvent<ErrorData>(sessionId, SessionEventCode.ERROR_EVT, data, id, timestamp)
