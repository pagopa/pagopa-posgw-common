package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that a session expired.
 *
 * @param sessionId identifier of the session
 * @param data status at the time of expiry
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class ExpiredEvent(
    sessionId: UUID,
    data: ExpiredData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) : SessionEvent<ExpiredData>(sessionId, SessionEventCode.EXPIRED_EVT, data, id, timestamp)
