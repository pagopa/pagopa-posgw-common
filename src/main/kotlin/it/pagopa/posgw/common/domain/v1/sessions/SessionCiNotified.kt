package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifiedErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionCiNotified(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val createdAt: Instant
) :
    Session,
    BaseSession(
        sessionId,
        correlationId,
        status = SessionStatus.IN_PROGRESS,
        createdAt = createdAt,
        updatedAt = Instant.now()) {

    constructor(
        session: BaseSession
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        createdAt = session.createdAt)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommerceNotifyEvent -> SessionEcommerceNotified(this)
            is EcommerceNotifiedErrorEvent -> SessionEcommerceNotifyError(this)
            else -> this
        }
}
