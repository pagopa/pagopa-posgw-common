package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosSessionInitializedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.CreditorIstitution
import it.pagopa.posgw.common.domain.v1.Psp
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionPosInizialialized(
    override val sessionId: SessionId,
    override val correlationId: String,
    val psp: Psp,
    val creditorIstitution: CreditorIstitution,
    val terminalIds: List<String>
) :
    Session,
    BaseSession(sessionId, correlationId, SessionStatus.IN_PROGRESS, Instant.now(), Instant.now()) {

    constructor(
        event: PosSessionInitializedEvent
    ) : this(
        event.sessionId,
        event.data.correlationId,
        event.data.psp,
        event.data.creditorInstitution,
        event.data.terminalIds)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is DeptPositionCreatedEvent -> SessionDebtPositionCreated(this, event)
            is TransactionActivatedEvent -> SessionTransactionActivated(this, event)
            is ErrorEvent -> SessionError(this, event)
            is ExpiredEvent -> SessionExpired(this, event)
            else -> this
        }
}
