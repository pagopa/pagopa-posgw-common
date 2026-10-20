package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import java.time.Instant

/**
 * Terminal session state created when a session-level error occurs.
 *
 * **Created from:** [Initialized], [DebtPositionCreated], [TransactionActivated], or
 * [AuthorizationRequestedError] via [ErrorEvent].
 *
 * **Transitions:** None; later events leave this state unchanged.
 *
 * @param initializedSession state that encountered the error
 * @param updatedAt time of the error event
 * @param data error details
 */
class Error(initializedSession: BaseSessionInitialized, updatedAt: Instant, val data: ErrorData) :
    Session,
    BaseSessionError(
        sessionInitialized = initializedSession, errorData = data, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.ERROR
    /** State from which this error state was created. */
    val sessionBeforeError: BaseSessionInitialized = initializedSession

    constructor(
        session: BaseSessionInitialized,
        event: ErrorEvent
    ) : this(initializedSession = session, updatedAt = event.timestamp, data = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
