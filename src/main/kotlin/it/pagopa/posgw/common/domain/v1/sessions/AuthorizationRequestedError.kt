package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

/**
 * Session state representing a failed authorization request and any subsequent retry.
 *
 * **Created from:**
 * - [TransactionActivated] via [AuthRequestErrorEvent]
 * - [AuthorizationRequestedError] via [AuthRequestRetriedEvent]
 *
 * **Transitions:**
 * - [AuthorizationRequested] via [AuthRequestedEvent]
 * - [AuthorizationRequestedError] via [AuthRequestRetriedEvent]
 * - [Error] via [ErrorEvent]
 * - [ExpiredToReconcile] via [ExpiredEvent]
 *
 * @param sessionWithTransactionAct state from which authorization was requested
 * @param authRequestErrorData data describing the request failure
 * @param authRequestRetriedData latest retry data, or `null` before a retry
 * @param updatedAt time of the latest session event
 */
class AuthorizationRequestedError(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    authRequestErrorData: AuthRequestErrorData,
    authRequestRetriedData: AuthRequestRetriedData?,
    updatedAt: Instant,
) :
    Session,
    BaseSessionWithAuthError(
        sessionWithTransactionAct = sessionWithTransactionAct,
        authRequestErrorData = authRequestErrorData,
        authRequestRetriedData = authRequestRetriedData,
        updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.AUTH_REQUEST_ERROR

    /** Creates this state from the original authorization request error event. */
    constructor(
        session: BaseSessionWithTransactionAct,
        event: AuthRequestErrorEvent
    ) : this(
        sessionWithTransactionAct = session,
        authRequestErrorData = event.data,
        authRequestRetriedData = null,
        updatedAt = event.timestamp)

    /** Creates an updated error state from a retry event while retaining the original error. */
    constructor(
        session: BaseSessionWithAuthError,
        event: AuthRequestRetriedEvent
    ) : this(
        sessionWithTransactionAct = session,
        authRequestErrorData = session.authRequestErrorData,
        authRequestRetriedData = event.data,
        updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthRequestedEvent -> AuthorizationRequested(this, event)
            is AuthRequestRetriedEvent -> AuthorizationRequestedError(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> ExpiredToReconcile(this, event)
            else -> this
        }
}
