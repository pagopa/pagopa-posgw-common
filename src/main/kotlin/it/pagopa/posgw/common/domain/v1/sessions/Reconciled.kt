package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ReconciledEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpiredToReconcile
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionReconciled
import java.time.Instant

/**
 * Terminal session state reached after an expired transaction is reconciled.
 *
 * **Created from:** [ExpiredToReconcile] via [ReconciledEvent].
 *
 * **Transitions:** None; later events leave this state unchanged.
 *
 * @param sessionExpiredToReconcile expired state being reconciled
 * @param updatedAt time of the reconciliation event
 */
class Reconciled(sessionExpiredToReconcile: BaseSessionExpiredToReconcile, updatedAt: Instant) :
    Session,
    BaseSessionReconciled(sessionToReconcile = sessionExpiredToReconcile, updatedAt = updatedAt) {

    constructor(
        sessionExpiredToReconcile: BaseSessionExpiredToReconcile,
        event: ReconciledEvent
    ) : this(sessionExpiredToReconcile = sessionExpiredToReconcile, updatedAt = event.timestamp)

    override val status: SessionStatus = SessionStatus.RECONCILED

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
