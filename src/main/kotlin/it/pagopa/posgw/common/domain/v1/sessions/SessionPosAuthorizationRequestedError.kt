package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionPosAuthorizationRequestedError(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val status: SessionStatus,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : Session, BaseSession(sessionId, correlationId, status, createdAt, updatedAt) {
    constructor(
        session: BaseSession,
        event: PosAuthorizationRequestErrorEvent
    ) : this(
        sessionId = SessionId(),
        correlationId = session.correlationId,
        status = SessionStatus.ERROR, // TODO check if this status is correct
        createdAt = session.createdAt,
        updatedAt = Instant.now(),
    )

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is PosAuthorizationRequestEvent -> SessionPosAuthorizationRequested(this, event)
            is ErrorEvent -> SessionError(this, event)
            is ExpiredEvent -> SessionExpired(this, event)
            else -> this
        }
}
