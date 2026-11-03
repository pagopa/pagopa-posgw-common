package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records an eCommerce notification failure.
 *
 * @param sessionId identifier of the session
 * @param data notification error details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class EcommNtfErrorEvent(
    sessionId: UUID,
    data: EcommNtfErrorData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<EcommNtfErrorData>(
        sessionId, SessionEventCode.ECOMM_NTF_ERROR_EVT, data, id, timestamp)
