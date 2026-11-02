package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a retry of a failed creditor-institution notification.
 *
 * @param sessionId identifier of the session
 * @param data retry count and error details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class CiNtfRetriedEvent(
    sessionId: UUID,
    data: CiNtfRetriedData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) :
    SessionEvent<CiNtfRetriedData>(
        sessionId, SessionEventCode.CI_NTF_RETRIED_EVT, data, id, timestamp)
