package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records reconciliation of an expired session.
 *
 * @param sessionId identifier of the session
 */
@Document(collection = "eventstore")
data class ReconciledEvent(override val sessionId: UUID) :
    SessionEvent<Unit>(sessionId, SessionEventCode.RECONCILED_EVT, Unit)
