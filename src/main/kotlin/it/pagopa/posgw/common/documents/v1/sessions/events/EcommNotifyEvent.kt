package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that the eCommerce notification completed for a session.
 *
 * @param sessionId identifier of the session
 */
@Document(collection = "eventstore")
data class EcommNotifyEvent(
    override val sessionId: UUID,
) :
    SessionEvent<Unit>(
        eventCode = SessionEventCode.ECOMM_NOTIFIED_EVT, data = Unit, sessionId = sessionId)
