package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionReconciled
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

class Reconciled(sessionWithTransactionAct: BaseSessionWithTransactionAct, updatedAt: Instant) :
    Session,
    BaseSessionReconciled(
        sessionWithTransactionAct = sessionWithTransactionAct, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.RECONCILED

    override fun applyEvent(event: SessionEvent<*>): Session {
        TODO("Not yet implemented")
    }
}
