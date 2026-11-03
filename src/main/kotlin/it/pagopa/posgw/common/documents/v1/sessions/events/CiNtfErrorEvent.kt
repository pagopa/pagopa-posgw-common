package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import java.time.Instant
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a creditor-institution notification failure.
 *
 * @param sessionId identifier of the session
 * @param data notification error details
 * @param id event identifier, generated for new events
 * @param timestamp time the event occurred, set to now for new events
 */
@Document(collection = "eventstore")
class CiNtfErrorEvent(
    sessionId: UUID,
    data: CiNtfErrorData,
    id: String = UUID.randomUUID().toString(),
    timestamp: Instant = Instant.now()
) : SessionEvent<CiNtfErrorData>(sessionId, SessionEventCode.CI_NTF_ERROR_EVT, data, id, timestamp)
