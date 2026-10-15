package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifiedErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommerceNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfError
import java.time.Instant

class EcommerceNotifyError(sessionWithCiNotified: BaseSessionWithCiNotified, updatedAt: Instant) :
    Session, BaseSessionWithEcommNtfError(sessionWithCiNotified, updatedAt) {

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_ERROR

    constructor(
        session: BaseSessionWithCiNotified,
        event: EcommerceNotifiedErrorEvent
    ) : this(sessionWithCiNotified = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommerceNotifyEvent -> EcommerceNotified(this, event)
            else -> this
        }
}
