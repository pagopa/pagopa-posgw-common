package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthorizationOutcomeReceivedData
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.AuthDetails
import it.pagopa.posgw.common.domain.v1.InstrumentDetails
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

class SessionAuthorizationOutcomeReceived(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val createdAt: Instant,
    val authDetails: AuthDetails,
    val instrumentDetails: InstrumentDetails
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
        event: SessionEvent<AuthorizationOutcomeReceivedData>
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        createdAt = session.createdAt,
        authDetails = event.data.authDetails,
        instrumentDetails = event.data.instrumentDetails)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is CiNotifiedEvent -> SessionCiNotified(this)
            is CiNotifiedErrorEvent -> SessionCiNotifyError(this)
            else -> this
        }
}
