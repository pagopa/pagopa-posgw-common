package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.BaseSessionEvent
import java.time.Instant.now
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Base type for persisted events that describe changes to a payment session.
 *
 * The event identifier and occurrence timestamp are generated when an event is created.
 *
 * @param T type of the event-specific payload
 * @param sessionId identifier of the session changed by this event
 * @param eventCode serialized event type
 * @param data event-specific payload
 */
@Document(collection = "eventstore")
sealed class SessionEvent<T>
protected constructor(sessionId: UUID, eventCode: SessionEventCode, data: T) :
    BaseSessionEvent<T>(
        UUID.randomUUID().toString(), timestamp = now(), sessionId, eventCode.name, data)
