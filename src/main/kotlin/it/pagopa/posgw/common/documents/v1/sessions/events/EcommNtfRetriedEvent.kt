package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a retry of a failed eCommerce notification.
 *
 * @param sessionId identifier of the session
 * @param data retry count and error details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class EcommNtfRetriedEvent(
    sessionId: UUID,
    data: EcommNtfRetriedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<EcommNtfRetriedData>(
        sessionId, SessionEventCode.ECOMM_NTF_RETRIED_EVT, data, id, timestamp)
