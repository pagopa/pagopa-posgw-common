package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpiredToReconcile
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

class ExpiredToReconcile(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    updatedAt: Instant
) : Session, BaseSessionExpiredToReconcile(sessionWithTransactionAct, updatedAt = updatedAt) {

    constructor(
        session: BaseSessionWithTransactionAct,
        event: ExpiredEvent
    ) : this(sessionWithTransactionAct = session, updatedAt = event.timestamp)

    override val status: SessionStatus = SessionStatus.EXPIRED_TO_RECONCILE

    override fun applyEvent(event: SessionEvent<*>): Session {
        TODO("Not yet implemented")
    }
}
