package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionInitializedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.CorrelationId
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized

/**
 * Session state created from the initialization event.
 *
 * **Created from:** [EmptySession] via [SessionInitializedEvent].
 *
 * **Transitions:**
 * - [DebtPositionCreated] via [DeptPositionCreatedEvent]
 * - [TransactionActivated] via [TransactionActivatedEvent]
 * - [Error] via [ErrorEvent]
 * - [Expired] via [ExpiredEvent]
 *
 * @param session empty state receiving the initialization event
 * @param event session initialization event
 */
class Initialized(session: EmptySession, event: SessionInitializedEvent) :
    Session,
    BaseSessionInitialized(
        initializeData = event.data,
        sessionId = SessionId(event.sessionId),
        createdAt = event.timestamp,
        correlationId = CorrelationId.of(event.data.correlationId),
        updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.INITIALIZED

    constructor(event: SessionInitializedEvent) : this(EmptySession(), event)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is DeptPositionCreatedEvent -> DebtPositionCreated(this, event)
            is TransactionActivatedEvent -> TransactionActivated(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
