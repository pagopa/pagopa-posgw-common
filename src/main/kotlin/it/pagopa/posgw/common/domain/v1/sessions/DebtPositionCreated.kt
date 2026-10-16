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
