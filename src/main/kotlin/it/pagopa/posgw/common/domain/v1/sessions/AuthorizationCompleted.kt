package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthReq
import java.time.Instant

/**
 * Session state reached after an authorization outcome is received.
 *
 * The authorization request and outcome data remain available for later notification states.
 *
 * **Created from:** [AuthorizationRequested] via [AuthOutcomeReceivedEvent].
 *
 * **Transitions:**
 * - [CiNotified] via [CiNotifiedEvent]
 * - [CiNotifyError] via [CiNtfErrorEvent]
 *
 * @param sessionWithAuthReq state that submitted the authorization request
 * @param authorizationData received authorization outcome
 * @param updatedAt time of the authorization outcome event
 */
class AuthorizationCompleted(
    sessionWithAuthReq: BaseSessionWithAuthReq,
    authorizationData: AuthOutcomeReceivedData,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthReq, authorizationData, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.AUTH_COMPLETED

    constructor(
        session: BaseSessionWithAuthReq,
        event: AuthOutcomeReceivedEvent
    ) : this(
        sessionWithAuthReq = session, authorizationData = event.data, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            is CiNtfErrorEvent -> CiNotifyError(this, event)
            else -> this
        }
}
