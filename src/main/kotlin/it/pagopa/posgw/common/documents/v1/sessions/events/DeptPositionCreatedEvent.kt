package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records creation of debt positions for a session.
 *
 * @param sessionId identifier of the session
 * @param data created debt-position data
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class DeptPositionCreatedEvent(
    sessionId: UUID,
    data: DebtPositionCreatedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<DebtPositionCreatedData>(
        sessionId, SessionEventCode.DEBT_POSITION_CREATED_EVT, data, id, timestamp)
