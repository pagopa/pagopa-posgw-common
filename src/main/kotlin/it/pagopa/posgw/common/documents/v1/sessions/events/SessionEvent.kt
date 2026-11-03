package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.BaseSessionEvent
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Base type for persisted events that describe changes to a payment session.
 *
 * Concrete events generate the identifier and occurrence timestamp when created, and receive the
 * stored ones when read back from MongoDB.
 *
 * @param T type of the event-specific payload
 * @param sessionId identifier of the session changed by this event
 * @param eventCode serialized event type
 * @param data event-specific payload
 * @param id event identifier
 * @param timestamp time the event occurred
 */
@Document(collection = "eventstore")
sealed class SessionEvent<T>
protected constructor(
    sessionId: UUID,
    eventCode: SessionEventCode,
    data: T,
    id: String,
    timestamp: Instant
) : BaseSessionEvent<T>(id, timestamp, sessionId, eventCode.name, data)
