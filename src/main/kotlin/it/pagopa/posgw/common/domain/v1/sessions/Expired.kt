package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpired
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized

/**
 * Terminal session state created when a session expires without reconciliation.
 *
 * **Created from:** [Initialized], [DebtPositionCreated], or [TransactionActivated] via
 * [ExpiredEvent].
 *
 * **Transitions:** None; later events leave this state unchanged.
 *
 * @param session state that expired
 * @param event expiry event
 */
class Expired(session: BaseSessionInitialized, event: ExpiredEvent) :
    Session,
    BaseSessionExpired(
        previewSession = session, expiredData = event.data, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.EXPIRED

    val data: ExpiredData = event.data

    /** State from which this expired state was created. */
    val sessionBeforeExpired: BaseSessionInitialized = session

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
