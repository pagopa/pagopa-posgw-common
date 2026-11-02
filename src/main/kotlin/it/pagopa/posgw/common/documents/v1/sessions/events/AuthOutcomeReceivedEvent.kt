package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records the authorization outcome received for a session.
 *
 * @param sessionId identifier of the session
 * @param data authorization outcome and instrument details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class AuthOutcomeReceivedEvent(
    sessionId: UUID,
    data: AuthOutcomeReceivedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<AuthOutcomeReceivedData>(
        sessionId, SessionEventCode.AUTH_OUTCOME_RECEIVED_EVT, data, id, timestamp)
