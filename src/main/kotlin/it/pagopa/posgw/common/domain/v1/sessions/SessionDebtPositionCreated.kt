package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.DebtPosition
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionDebtPositionCreated(
    override val sessionId: SessionId,
    val depPositions: List<DebtPosition>,
    override val correlationId: String,
    override val createdAt: Instant,
) :
    Session,
    BaseSession(sessionId, correlationId, SessionStatus.IN_PROGRESS, createdAt, Instant.now()) {

    constructor(
        session: BaseSession,
        debtPositionCreatedEvent: DeptPositionCreatedEvent
    ) : this(
        sessionId = session.sessionId,
        createdAt = session.createdAt,
        correlationId = session.correlationId,
        depPositions = debtPositionCreatedEvent.data.depPositions)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is TransactionActivatedEvent -> SessionTransactionActivated(this, event)
            is ErrorEvent -> SessionError(this, event)
            is ExpiredEvent -> SessionExpired(this, event)
            else -> this
        }
}
