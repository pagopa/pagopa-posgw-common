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

/**
 * Session state representing an expired creditor-institution notification.
 *
 * **Created from:**
 * - [CiNotifyError] via [CiNotifyExpiredEvent]
 * - [CiNotifyExpired] via [CiNtfRetriedEvent]
 *
 * **Transitions:**
 * - [CiNotified] via [CiNotifiedEvent]
 * - [CiNotifyExpired] via [CiNtfRetriedEvent]
 * - [ExpiredToReconcile] via [ExpiredEvent]
 *
 * @param sessionWithCiNtfError failed notification state retained by this state
 * @param ciExpRetriedData retry data recorded after expiry, if any
 * @param updatedAt time of the latest session event
 */
class CiNotifyExpired(
    sessionWithCiNtfError: BaseSessionWithCiNtfError,
    ciExpRetriedData: CiNtfRetriedData?,
    updatedAt: Instant,
) :
    Session,
    BaseSessionWithCiNtfExpired(sessionWithCiNtfError, ciExpRetriedData, updatedAt = updatedAt) {

    constructor(
        session: BaseSessionWithCiNtfError,
        event: CiNotifyExpiredEvent
    ) : this(sessionWithCiNtfError = session, null, updatedAt = event.timestamp)

    constructor(
        session: BaseSessionWithCiNtfExpired,
        event: CiNtfRetriedEvent
    ) : this(
        sessionWithCiNtfError = session, ciExpRetriedData = event.data, updatedAt = event.timestamp)

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
