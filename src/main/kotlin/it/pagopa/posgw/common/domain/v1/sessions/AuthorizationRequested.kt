package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.PosAuthorizationRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthorizationOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosAuthorizationRequestEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthReq
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

class AuthorizationRequested(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    posAuthorizationRequestData: PosAuthorizationRequestedData,
    updatedAt: Instant,
) :
    Session,
    BaseSessionWithAuthReq(
        sessionWithTransactionAct = sessionWithTransactionAct,
        posAuthorizationRequestData = posAuthorizationRequestData,
        updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.AUTH_REQUESTED

    constructor(
        session: BaseSessionWithTransactionAct,
        event: PosAuthorizationRequestEvent
    ) : this(
        sessionWithTransactionAct = session,
        updatedAt = event.timestamp,
        posAuthorizationRequestData = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthorizationOutcomeReceivedEvent -> AuthorizationCompleted(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
