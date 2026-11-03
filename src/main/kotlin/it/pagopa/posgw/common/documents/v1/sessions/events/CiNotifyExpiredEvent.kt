package it.pagopa.posgw.common.documents.v1.sessions.events

import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that the creditor-institution notification expired.
 *
 * @param sessionId identifier of the session
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class CiNotifyExpiredEvent(
    sessionId: UUID,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) : SessionEvent<Nothing?>(sessionId, SessionEventCode.CI_NTF_EXPIRED_EVT, null, id, timestamp)
