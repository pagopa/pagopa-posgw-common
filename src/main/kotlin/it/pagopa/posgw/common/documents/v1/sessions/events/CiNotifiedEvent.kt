package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that the creditor institution was notified for a session.
 *
 * @param sessionId identifier of the session
 */
@Document(collection = "eventstore")
data class CiNotifiedEvent(override val sessionId: UUID) :
    SessionEvent<Unit>(
        eventCode = SessionEventCode.CI_NOTIFIED_EVT, data = Unit, sessionId = sessionId)
