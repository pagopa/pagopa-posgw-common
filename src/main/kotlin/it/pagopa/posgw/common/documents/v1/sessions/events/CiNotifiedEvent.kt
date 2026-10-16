package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class CiNotifiedEvent(override val sessionId: UUID) :
    SessionEvent<Unit>(
        eventCode = SessionEventCode.CI_NOTIFIED_EVT, data = Unit, sessionId = sessionId)
