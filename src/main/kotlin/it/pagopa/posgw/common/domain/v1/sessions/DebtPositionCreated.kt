package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithDebtPos

/**
 * Session state reached after a debt position is created.
 *
 * Its debt-position data is retained when the session advances to transaction activation.
 *
 * **Created from:** [Initialized] via [DeptPositionCreatedEvent].
 *
 * **Transitions:**
 * - [TransactionActivated] via [TransactionActivatedEvent]
 * - [Error] via [ErrorEvent]
 * - [Expired] via [ExpiredEvent]
 *
 * @param session initialized state from which the debt position was created
 * @param event debt-position-created event
 */
class DebtPositionCreated(session: BaseSessionInitialized, event: DeptPositionCreatedEvent) :
    Session,
    BaseSessionWithDebtPos(
        sessionInitialized = session, updatedAt = event.timestamp, debtPositionData = event.data) {

    override val status: SessionStatus = SessionStatus.DEBT_POSITION_CREATED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is TransactionActivatedEvent -> TransactionActivated(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
