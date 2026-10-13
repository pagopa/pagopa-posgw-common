package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.ActivationInfo
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionTransactionActivated(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val status: SessionStatus,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    val transactionId: String,
    val activationInfo: List<ActivationInfo>
) : Session, BaseSession(sessionId, correlationId, status, createdAt, updatedAt) {

    constructor(
        session: BaseSession,
        event: TransactionActivatedEvent
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        status = SessionStatus.IN_PROGRESS,
        createdAt = session.createdAt,
        updatedAt = Instant.now(),
        transactionId = event.data.transactionId,
        activationInfo = event.data.activationInfo)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is PosAuthorizationRequestEvent -> SessionPosAuthorizationRequested(this, event)
            is PosAuthorizationRequestErrorEvent ->
                SessionPosAuthorizationRequestedError(this, event)
            is ErrorEvent -> SessionError(this, event)
            is ExpiredEvent -> SessionExpired(this, event)
            else -> this
        }
}
