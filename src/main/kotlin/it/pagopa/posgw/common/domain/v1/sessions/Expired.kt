package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionExpired
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionInitialized
import java.time.Instant

class Expired(
    initializedSession: BaseSessionInitialized,
    updatedAt: Instant,
    val data: ExpiredData
) :
    Session,
    BaseSessionExpired(
        previewSession = initializedSession, expiredData = data, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.EXPIRED
    val sessionBeforeExpired: BaseSessionInitialized = initializedSession

    constructor(
        session: BaseSessionInitialized,
        event: ExpiredEvent
    ) : this(initializedSession = session, updatedAt = event.timestamp, data = event.data)

    override fun applyEvent(event: SessionEvent<*>): Session = this
}
