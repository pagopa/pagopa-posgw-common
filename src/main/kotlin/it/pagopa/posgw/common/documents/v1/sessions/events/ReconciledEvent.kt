package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class ReconciledEvent(override val sessionId: UUID) :
    SessionEvent<Unit>(sessionId, SessionEventCode.RECONCILED_EVT, Unit)
