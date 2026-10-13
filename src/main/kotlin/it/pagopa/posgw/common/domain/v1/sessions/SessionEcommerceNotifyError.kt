package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionEcommerceNotifyError(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val createdAt: Instant,
) :
    Session,
    BaseSession(
        sessionId,
        correlationId,
        status = SessionStatus.ERROR, // TODO check if this status is correct
        createdAt,
        updatedAt = Instant.now()) {

    constructor(
        session: BaseSession
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        createdAt = session.createdAt)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            else -> this
        }
}
