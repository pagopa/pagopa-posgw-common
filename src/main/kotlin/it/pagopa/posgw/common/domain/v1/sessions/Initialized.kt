package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.PosSessionInitializedData
import it.pagopa.posgw.common.documents.v1.sessions.events.DeptPositionCreatedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.PosSessionInitializedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.TransactionActivatedEvent
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import java.time.Instant

class Initialized(
    sessionId: SessionId,
    correlationId: String,
    createdAt: Instant,
    initializeData: PosSessionInitializedData
) :
    Session,
    BaseSessionInitialized(initializeData, sessionId, createdAt, correlationId, createdAt) {

    override val status: SessionStatus = SessionStatus.INITIALIZED

    constructor(
        event: PosSessionInitializedEvent
    ) : this(event.sessionId, event.data.correlationId, event.timestamp, event.data)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is DeptPositionCreatedEvent -> DebtPositionCreated(this, event)
            is TransactionActivatedEvent -> TransactionActivated(this, event)
            is ErrorEvent -> Error(this, event)
            is ExpiredEvent -> Expired(this, event)
            else -> this
        }
}
