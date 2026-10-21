package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized

/**
 * Terminal session state created when a session-level error occurs.
 *
 * **Created from:** [Initialized], [DebtPositionCreated], [TransactionActivated], or
 * [AuthorizationRequestedError] via [ErrorEvent].
 *
 * **Transitions:** None; later events leave this state unchanged.
 *
 * @param session state that encountered the error
 * @param event error event
 */
class Error(session: BaseSessionInitialized, event: ErrorEvent) :
    Session,
    BaseSessionError(
        sessionInitialized = session, errorData = event.data, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.ERROR

    val data: ErrorData = event.data

    /** State from which this error state was created. */
    val sessionBeforeError: BaseSessionInitialized = session

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
