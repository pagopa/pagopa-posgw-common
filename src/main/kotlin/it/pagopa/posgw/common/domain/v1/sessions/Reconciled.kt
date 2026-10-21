package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ReconciledEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpiredToReconcile
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionReconciled

/**
 * Terminal session state reached after an expired transaction is reconciled.
 *
 * **Created from:** [ExpiredToReconcile] via [ReconciledEvent].
 *
 * **Transitions:** None; later events leave this state unchanged.
 *
 * @param session expired state being reconciled
 * @param event reconciliation event
 */
class Reconciled(session: BaseSessionExpiredToReconcile, event: ReconciledEvent) :
    Session, BaseSessionReconciled(sessionToReconcile = session, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.RECONCILED

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
