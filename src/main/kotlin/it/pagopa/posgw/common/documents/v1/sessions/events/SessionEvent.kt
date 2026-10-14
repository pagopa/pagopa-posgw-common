package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.BaseSessionEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import java.time.Instant.now
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
sealed class SessionEvent<T>
protected constructor(sessionId: SessionId, eventCode: SessionEventCode, data: T) :
    BaseSessionEvent<T>(
        UUID.randomUUID().toString(), timestamp = now(), sessionId, eventCode.code, data) {

    init {
        // TODO: verify if this is the correct message to throw
        require(sessionId.toString().length == 32) {
            "SessionId must be 32 characters long" // to verify
        }
    }
}
