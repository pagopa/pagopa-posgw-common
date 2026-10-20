package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import java.time.Instant

class Error(initializedSession: BaseSessionInitialized, updatedAt: Instant, val data: ErrorData) :
    Session,
    BaseSessionError(
        sessionInitialized = initializedSession, errorData = data, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.ERROR
    val sessionBeforeError: BaseSessionInitialized = initializedSession

    constructor(
        session: BaseSessionInitialized,
        event: ErrorEvent
    ) : this(initializedSession = session, updatedAt = event.timestamp, data = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
