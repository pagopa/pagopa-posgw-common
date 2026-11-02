package it.pagopa.posgw.common.documents.v1.sessions.events

import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records reconciliation of an expired session.
 *
 * @param sessionId identifier of the session
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class ReconciledEvent(
    sessionId: UUID,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) : SessionEvent<Nothing?>(sessionId, SessionEventCode.RECONCILED_EVT, null, id, timestamp)
