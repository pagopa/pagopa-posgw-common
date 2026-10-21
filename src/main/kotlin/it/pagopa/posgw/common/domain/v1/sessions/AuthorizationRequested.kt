package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.AuthOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthReq
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct

/**
 * Session state while an authorization request is awaiting its outcome.
 *
 * **Created from:** [TransactionActivated] or [AuthorizationRequestedError] via
 * [AuthRequestedEvent].
 *
 * **Transitions:**
 * - [AuthorizationCompleted] via [AuthOutcomeReceivedEvent]
 * - [ExpiredToReconcile] via [ExpiredEvent]
 *
 * @param session state from which authorization was requested
 * @param event authorization request event
 */
class AuthorizationRequested(session: BaseSessionWithTransactionAct, event: AuthRequestedEvent) :
    Session,
    BaseSessionWithAuthReq(
        sessionWithTransactionAct = session,
        authorizationRequestData = event.data,
        updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.AUTH_REQUESTED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthOutcomeReceivedEvent -> AuthorizationCompleted(this, event)
            is ExpiredEvent -> ExpiredToReconcile(this, event)
            else -> this
        }
}
