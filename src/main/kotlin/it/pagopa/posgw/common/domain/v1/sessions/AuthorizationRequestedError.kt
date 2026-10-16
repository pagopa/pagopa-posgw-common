package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

class AuthorizationRequestedError(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    updatedAt: Instant,
) :
    Session,
    BaseSessionWithAuthError(
        sessionWithTransactionAct = sessionWithTransactionAct, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.AUTH_REQUEST_ERROR

    constructor(
        session: BaseSessionWithTransactionAct,
        event: PosAuthorizationRequestErrorEvent
    ) : this(sessionWithTransactionAct = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is PosAuthorizationRequestEvent -> AuthorizationRequested(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
