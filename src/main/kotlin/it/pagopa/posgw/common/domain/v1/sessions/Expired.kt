package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import java.time.Instant

class Expired(
    initializedSession: BaseSessionInitialized,
    updatedAt: Instant,
    val data: ExpiredData
) :
    Session,
    BaseSessionInitialized(
        sessionId = initializedSession.sessionId,
        correlationId = initializedSession.correlationId,
        createdAt = initializedSession.createdAt,
        initializeData = initializedSession.initializeData,
        updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.EXPIRED

    constructor(
        session: BaseSessionInitialized,
        event: ExpiredEvent
    ) : this(initializedSession = session, updatedAt = event.timestamp, data = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
