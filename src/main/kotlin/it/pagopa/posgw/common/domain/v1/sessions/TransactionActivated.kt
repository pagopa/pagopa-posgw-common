package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct

/**
 * Session state reached after transaction activation.
 *
 * **Created from:** [Initialized] or [DebtPositionCreated] via [TransactionActivatedEvent].
 *
 * **Transitions:**
 * - [AuthorizationRequested] via [AuthRequestedEvent]
 * - [AuthorizationRequestedError] via [AuthRequestErrorEvent]
 * - [Error] via [ErrorEvent]
 * - [Expired] via [ExpiredEvent]
 *
 * @param session initialized or debt-position-created predecessor state
 * @param event transaction-activation event
 */
class TransactionActivated(session: BaseSessionInitialized, event: TransactionActivatedEvent) :
    Session,
    BaseSessionWithTransactionAct(
        initializedSession = session,
        transactionActivateData = event.data,
        updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.TRANSACTION_ACTIVATED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthRequestedEvent -> AuthorizationRequested(this, event)
            is AuthRequestErrorEvent -> AuthorizationRequestedError(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
