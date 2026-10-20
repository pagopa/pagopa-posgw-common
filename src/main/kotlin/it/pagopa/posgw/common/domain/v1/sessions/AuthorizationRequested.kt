package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthOutcomeReceivedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthReq
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

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
 * @param sessionWithTransactionAct state from which authorization was requested
 * @param authorizationRequestData data sent with the authorization request
 * @param updatedAt time of the latest session event
 */
class AuthorizationRequested(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    authorizationRequestData: AuthRequestedData,
    updatedAt: Instant,
) :
    Session,
    BaseSessionWithAuthReq(
        sessionWithTransactionAct = sessionWithTransactionAct,
        authorizationRequestData = authorizationRequestData,
        updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.AUTH_REQUESTED

    constructor(
        session: BaseSessionWithTransactionAct,
        event: AuthRequestedEvent
    ) : this(
        sessionWithTransactionAct = session,
        updatedAt = event.timestamp,
        authorizationRequestData = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthOutcomeReceivedEvent -> AuthorizationCompleted(this, event)
            is ExpiredEvent -> ExpiredToReconcile(this, event)
            else -> this
        }
}
