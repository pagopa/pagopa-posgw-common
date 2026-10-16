package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfExpired
import java.time.Instant

class EcommerceNotifyExpired(
    sessionWithEcommNtfError: BaseSessionWithEcommNtfExpired,
    updatedAt: Instant
) : Session, BaseSessionWithEcommNtfExpired(sessionWithEcommNtfError, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_EXPIRED

    override fun applyEvent(event: SessionEvent<*>): Session {
        TODO("Not yet implemented")
    }
}
