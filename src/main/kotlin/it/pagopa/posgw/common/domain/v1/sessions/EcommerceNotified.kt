package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNotified
import java.time.Instant

class EcommerceNotified(sessionWithCiNotified: BaseSessionWithCiNotified, updatedAt: Instant) :
    Session, BaseSessionWithEcommNotified(sessionWithCiNotified, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.ECOMM_NOTIFIED

    constructor(
        session: BaseSessionWithCiNotified,
        event: EcommerceNotifyEvent
    ) : this(sessionWithCiNotified = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session {
        return this
    }
}
