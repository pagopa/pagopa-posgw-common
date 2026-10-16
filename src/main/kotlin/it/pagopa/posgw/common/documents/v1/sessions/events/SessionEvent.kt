package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.BaseSessionEvent
import java.time.Instant.now
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
sealed class SessionEvent<T>
protected constructor(sessionId: UUID, eventCode: SessionEventCode, data: T) :
    BaseSessionEvent<T>(
        UUID.randomUUID().toString(), timestamp = now(), sessionId, eventCode.name, data)
