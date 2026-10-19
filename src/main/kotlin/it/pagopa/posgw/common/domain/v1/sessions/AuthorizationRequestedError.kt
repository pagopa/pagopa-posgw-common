package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
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

    /**
     * Constructor used to create an instance of AuthorizationRequestedError from a BaseSessionWithTransactionAct and an AuthRequestErrorEvent.
     */
    constructor(
        session: BaseSessionWithTransactionAct,
        event: AuthRequestErrorEvent
    ) : this(sessionWithTransactionAct = session, updatedAt = event.timestamp)

    /**
     * Constructor used to create an instance of AuthorizationRequestedError from a BaseSessionWithTransactionAct and an AuthRequestRetriedEvent.
     */
    constructor(
        session: BaseSessionWithTransactionAct,
        event: AuthRequestRetriedEvent
    ) : this(sessionWithTransactionAct = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthRequestedEvent -> AuthorizationRequested(this, event)
            is AuthRequestRetriedEvent -> AuthorizationRequestedError(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> ExpiredToReconcile(this, event)
            else -> this
        }
}
