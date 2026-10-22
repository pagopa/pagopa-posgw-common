package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ReconciledEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpiredToReconcile
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfExpired
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct

/**
 * Session state awaiting reconciliation after expiry during transaction processing.
 *
 * **Created from:** [AuthorizationRequested], [AuthorizationRequestError], or [CiNotifyExpired] via
 * [ExpiredEvent].
 *
 * **Transitions:**
 * - [Reconciled] via [ReconciledEvent]
 *
 * @param session transaction state from which expiry occurred
 * @param event expiry event
 */
class ExpiredToReconcile(session: BaseSessionWithTransactionAct, event: ExpiredEvent) :
    Session,
    BaseSessionExpiredToReconcile(session, data = event.data, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.EXPIRED_TO_RECONCILE

    constructor(
        sessionWithCiNtfExpired: BaseSessionWithCiNtfExpired,
        event: ExpiredEvent
    ) : this(session = sessionWithCiNtfExpired, event = event)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is ReconciledEvent -> Reconciled(this, event)
            else -> this
        }
}
