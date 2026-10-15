package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import java.time.Instant

class Error(inizializedSession: BaseSessionInitialized, updatedAt: Instant, val data: ErrorData) :
    Session,
    BaseSessionInitialized(
        sessionId = inizializedSession.sessionId,
        correlationId = inizializedSession.correlationId,
        createdAt = inizializedSession.createdAt,
        initializeData = inizializedSession.initializeData,
        updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.ERROR

    constructor(
        session: BaseSessionInitialized,
        event: ErrorEvent
    ) : this(inizializedSession = session, updatedAt = event.timestamp, data = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
