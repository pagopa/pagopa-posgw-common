package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.Steps
import java.time.Instant

class SessionExpired(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    val step: Steps
) :
    Session,
    BaseSession(
        sessionId = sessionId,
        correlationId = correlationId,
        status = SessionStatus.EXPIRED,
        createdAt = createdAt,
        updatedAt = Instant.now()) {

    constructor(
        session: BaseSession,
        event: ExpiredEvent
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        createdAt = session.createdAt,
        updatedAt = Instant.now(),
        step = event.data.step)

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
