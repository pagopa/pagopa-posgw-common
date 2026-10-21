package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.AuthOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthReq

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
 * @param session state that submitted the authorization request
 * @param event authorization outcome event
 */
class AuthorizationCompleted(session: BaseSessionWithAuthReq, event: AuthOutcomeReceivedEvent) :
    Session,
    BaseSessionWithAuthComp(
        sessionWithAuthReq = session, authorizationData = event.data, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.AUTH_COMPLETED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            is CiNtfErrorEvent -> CiNotifyError(this, event)
            else -> this
        }
}
