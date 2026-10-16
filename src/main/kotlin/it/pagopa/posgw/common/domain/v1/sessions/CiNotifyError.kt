package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfError
import java.time.Instant

class CiNotifyError(sessionWithAuthComp: BaseSessionWithAuthComp, updatedAt: Instant) :
    Session, BaseSessionWithCiNtfError(sessionWithAuthComp, updatedAt) {

    override val status: SessionStatus = SessionStatus.CI_NTF_ERROR

    constructor(
        session: BaseSessionWithAuthComp,
        event: CiNotifiedErrorEvent
    ) : this(sessionWithAuthComp = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session {
        return when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            else -> this
        }
    }
}
