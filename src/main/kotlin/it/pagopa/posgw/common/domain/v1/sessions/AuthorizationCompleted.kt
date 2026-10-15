package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthorizationOutcomeReceivedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthorizationOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthReq
import java.time.Instant

class AuthorizationCompleted(
    sessionWithAuthReq: BaseSessionWithAuthReq,
    authorizationData: AuthorizationOutcomeReceivedData,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthReq, authorizationData, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.AUTH_COMPLETED

    constructor(
        session: BaseSessionWithAuthReq,
        event: AuthorizationOutcomeReceivedEvent
    ) : this(
        sessionWithAuthReq = session, authorizationData = event.data, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            is CiNotifiedErrorEvent -> CiNotifyError(this, event)
            else -> this
        }
}
