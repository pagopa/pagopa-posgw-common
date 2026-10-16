package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfExpired
import java.time.Instant

class CiNotifyExpired(
    sessionWithCiNtfError: BaseSessionWithCiNtfError,
    updatedAt: Instant,
) : Session, BaseSessionWithCiNtfExpired(sessionWithCiNtfError, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.CI_NTF_EXPIRED

    //    constructor(
    //        session: BaseSessionWithCiNtfError,
    //        event: //TODO ADD RIGHT EVENT
    //    ) : this(
    //        sessionWithCiNtfError = session,
    //        updatedAt = event.timestamp
    //    )

    override fun applyEvent(event: SessionEvent<*>): Session {
        TODO("Not yet implemented")
    }
}
