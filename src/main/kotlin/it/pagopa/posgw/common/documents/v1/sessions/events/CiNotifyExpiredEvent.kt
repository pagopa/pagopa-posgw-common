package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records that the creditor-institution notification expired.
 *
 * @param sessionId identifier of the session
 */
@Document(collection = "eventstore")
data class CiNotifyExpiredEvent(override val sessionId: UUID) :
    SessionEvent<Unit>(sessionId, SessionEventCode.CI_NTF_EXPIRED_EVT, Unit)
