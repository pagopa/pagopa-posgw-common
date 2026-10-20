package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a creditor-institution notification failure.
 *
 * @param sessionId identifier of the session
 * @param data notification error details
 */
@Document(collection = "eventstore")
data class CiNtfErrorEvent(override val sessionId: UUID, override val data: CiNtfErrorData) :
    SessionEvent<CiNtfErrorData>(
        eventCode = SessionEventCode.CI_NTF_ERROR_EVT, data = data, sessionId = sessionId)
