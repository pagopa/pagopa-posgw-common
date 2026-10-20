package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithDebtPos
import java.time.Instant

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
 * @param sessionInitialized initialized state from which the debt position was created
 * @param debtPositionData created debt-position data
 * @param updatedAt time of the debt-position-created event
 */
class DebtPositionCreated(
    sessionInitialized: BaseSessionInitialized,
    debtPositionData: DebtPositionCreatedData,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithDebtPos(
        sessionInitialized = sessionInitialized,
        updatedAt = updatedAt,
        debtPositionData = debtPositionData) {

    override val status: SessionStatus = SessionStatus.DEBT_POSITION_CREATED

    constructor(
        session: BaseSessionInitialized,
        event: DeptPositionCreatedEvent
    ) : this(
        sessionInitialized = session, updatedAt = event.timestamp, debtPositionData = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is TransactionActivatedEvent -> TransactionActivated(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
