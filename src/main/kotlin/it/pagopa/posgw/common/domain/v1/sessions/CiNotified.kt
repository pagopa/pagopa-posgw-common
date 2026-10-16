package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifiedErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import java.time.Instant

class CiNotified(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    updatedAt: Instant,
) : Session, BaseSessionWithCiNotified(sessionWithAuthComp, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.CI_NOTIFIED

    constructor(
        session: BaseSessionWithAuthComp,
        event: CiNotifiedEvent
    ) : this(sessionWithAuthComp = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommerceNotifyEvent -> EcommerceNotified(this, event)
            is EcommerceNotifiedErrorEvent -> EcommerceNotifyError(this, event)
            else -> this
        }
}
