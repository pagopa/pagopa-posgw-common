package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.AuthRequestedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithTransactionAct
import java.time.Instant

class TransactionActivated(
    transactionActivatedData: TransactionActivatedData,
    sessionInitialized: BaseSessionInitialized,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithTransactionAct(
        sessionInitialized = sessionInitialized,
        transactionActivateData = transactionActivatedData,
        updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.TRANSACTION_ACTIVATED

    constructor(
        session: BaseSessionInitialized,
        event: TransactionActivatedEvent
    ) : this(
        sessionInitialized = session,
        transactionActivatedData = event.data,
        updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is AuthRequestedEvent -> AuthorizationRequested(this, event)
            is AuthRequestErrorEvent -> AuthorizationRequestedError(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
