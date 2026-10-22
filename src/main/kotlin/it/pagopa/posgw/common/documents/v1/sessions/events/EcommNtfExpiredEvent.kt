package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that the eCommerce notification expired.
 *
 * @param sessionId identifier of the session
 */
@Document(collection = "eventstore")
data class EcommNtfExpiredEvent(override val sessionId: UUID) :
    SessionEvent<Unit>(
        eventCode = SessionEventCode.ECOMM_NTF_EXPIRED_EVT, data = Unit, sessionId = sessionId)
