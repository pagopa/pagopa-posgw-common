package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ReconciledEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpiredToReconcile
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfExpired
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

/**
 * Session state awaiting reconciliation after expiry during transaction processing.
 *
 * **Created from:** [AuthorizationRequested], [AuthorizationRequestedError], or [CiNotifyExpired]
 * via [ExpiredEvent].
 *
 * **Transitions:**
 * - [Reconciled] via [ReconciledEvent]
 *
 * @param sessionWithTransactionAct transaction state from which expiry occurred
 * @param expiredData details of the expiry
 * @param updatedAt time of the expiry event
 */
class ExpiredToReconcile(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    expiredData: ExpiredData,
    updatedAt: Instant
) :
    Session,
    BaseSessionExpiredToReconcile(sessionWithTransactionAct, data = expiredData, updatedAt) {

    override val status: SessionStatus = SessionStatus.EXPIRED_TO_RECONCILE

    constructor(
        session: BaseSessionWithTransactionAct,
        event: ExpiredEvent
    ) : this(sessionWithTransactionAct = session, event.data, updatedAt = event.timestamp)

    constructor(
        session: BaseSessionWithCiNtfExpired,
        event: ExpiredEvent
    ) : this(sessionWithTransactionAct = session, event.data, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is ReconciledEvent -> Reconciled(this, event)
            else -> this
        }
}
