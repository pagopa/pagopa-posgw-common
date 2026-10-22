package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that a session expired.
 *
 * @param sessionId identifier of the session
 * @param data status at the time of expiry
 */
@Document(collection = "eventstore")
data class ExpiredEvent(override val sessionId: UUID, override val data: ExpiredData) :
    SessionEvent<ExpiredData>(
        eventCode = SessionEventCode.EXPIRED_EVT, data = data, sessionId = sessionId)
