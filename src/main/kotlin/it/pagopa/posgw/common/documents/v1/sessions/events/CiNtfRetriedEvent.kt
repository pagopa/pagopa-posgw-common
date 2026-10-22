package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a retry of a failed creditor-institution notification.
 *
 * @param sessionId identifier of the session
 * @param data retry count and error details
 */
@Document(collection = "eventstore")
data class CiNtfRetriedEvent(override val sessionId: UUID, override val data: CiNtfRetriedData) :
    SessionEvent<CiNtfRetriedData>(
        eventCode = SessionEventCode.CI_NTF_RETRIED_EVT, data = data, sessionId = sessionId)
