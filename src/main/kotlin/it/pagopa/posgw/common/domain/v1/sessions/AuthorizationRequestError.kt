package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.*
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct

/**
 * Session state representing a failed authorization request and any subsequent retry.
 *
 * **Created from:**
 * - [TransactionActivated] via [AuthRequestErrorEvent]
 * - [AuthorizationRequestError] via [AuthRequestRetriedEvent]
 *
 * **Transitions:**
 * - [AuthorizationRequested] via [AuthRequestedEvent]
 * - [AuthorizationRequestError] via [AuthRequestRetriedEvent]
 * - [Error] via [ErrorEvent]
 * - [ExpiredToReconcile] via [ExpiredEvent]
 *
 * @param sessionWithTransactionAct transaction state retained by this error state
 * @param sourceEvent event that created or updated this error state
 * @param authRequestErrorData data describing the request failure
 * @param authRequestRetriedData latest retry data, or `null` before a retry
 */
class AuthorizationRequestError
private constructor(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    sourceEvent: SessionEvent<*>,
    authRequestErrorData: AuthRequestErrorData,
    authRequestRetriedData: List<AuthRequestRetriedData>,
) :
    Session,
    BaseSessionWithAuthError(
        sessionWithTransactionAct = sessionWithTransactionAct,
        authRequestErrorData = authRequestErrorData,
        authRequestRetriedData = authRequestRetriedData,
        updatedAt = sourceEvent.timestamp) {

    override val status: SessionStatus = SessionStatus.AUTH_REQUEST_ERROR

    /** Creates this state from the original authorization request error event. */
    constructor(
        session: BaseSessionWithTransactionAct,
        event: AuthRequestErrorEvent
    ) : this(
        sessionWithTransactionAct = session,
        sourceEvent = event,
        authRequestErrorData = event.data,
        authRequestRetriedData = emptyList())

    /** Creates an updated error state from a retry event while retaining the original error. */
    constructor(
        session: BaseSessionWithAuthError,
        event: AuthRequestRetriedEvent
    ) : this(
        sessionWithTransactionAct = session,
        sourceEvent = event,
        authRequestErrorData = session.authRequestErrorData,
        authRequestRetriedData = session.authRequestRetriedData + event.data)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthRequestedEvent -> AuthorizationRequested(this, event)
            is AuthRequestRetriedEvent -> AuthorizationRequestError(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> ExpiredToReconcile(this, event)
            else -> this
        }
}
