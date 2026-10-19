package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifyExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.ExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfExpired
import java.time.Instant

class CiNotifyExpired(
    sessionWithCiNtfError: BaseSessionWithCiNtfError,
    ciRetriedData: CiNtfRetriedData?,
    updatedAt: Instant,
) :
    Session,
    BaseSessionWithCiNtfExpired(sessionWithCiNtfError, ciRetriedData, updatedAt = updatedAt) {

    constructor(
        session: BaseSessionWithCiNtfError,
        event: CiNotifyExpiredEvent
    ) : this(sessionWithCiNtfError = session, null, updatedAt = event.timestamp)

    constructor(
        session: BaseSessionWithCiNtfExpired,
        event: CiNtfRetriedEvent
    ) : this(
        sessionWithCiNtfError = session.sessionWithCiNtfError,
        ciRetriedData = event.data,
        updatedAt = event.timestamp)

    override val status: SessionStatus = SessionStatus.CI_NTF_EXPIRED

    override fun applyEvent(event: SessionEvent<*>): Session {
        return when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            is CiNtfRetriedEvent -> CiNotifyExpired(this, event)
            is ExpiredEvent -> ExpiredToReconcile(this, event)
            else -> this
        }
    }
}
