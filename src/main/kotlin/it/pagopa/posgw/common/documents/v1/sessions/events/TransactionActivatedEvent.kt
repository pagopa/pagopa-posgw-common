package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records activation of a transaction for a session.
 *
 * @param sessionId identifier of the session
 * @param data transaction activation results
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class TransactionActivatedEvent(
    sessionId: UUID,
    data: TransactionActivatedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<TransactionActivatedData>(
        sessionId, SessionEventCode.TRANSACTION_ACTIVATED_EVT, data, id, timestamp)
