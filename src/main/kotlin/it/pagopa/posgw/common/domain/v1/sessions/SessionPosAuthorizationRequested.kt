package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.AuthorizationOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionPosAuthorizationRequested(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val createdAt: Instant,
    val terminalId: String,
    val authorizationId: String
) :
    Session,
    BaseSession(
        sessionId,
        correlationId,
        status = SessionStatus.IN_PROGRESS,
        createdAt,
        updatedAt = Instant.now()) {

    constructor(
        session: BaseSession,
        event: PosAuthorizationRequestEvent
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        createdAt = session.createdAt,
        terminalId = event.data.terminalId,
        authorizationId = event.data.authorizationId)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthorizationOutcomeReceivedEvent -> SessionAuthorizationOutcomeReceived(this, event)
            is ErrorEvent -> SessionError(this, event)
            is ExpiredEvent -> SessionExpired(this, event)
            else -> this
        }
}
