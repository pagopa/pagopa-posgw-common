package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.*
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfExpired

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
 * @param sourceEvent event that created or updated this expired state
 * @param ciExpRetriedData retry data recorded after expiry, if any
 */
class CiNotifyExpired
private constructor(
    sessionWithCiNtfError: BaseSessionWithCiNtfError,
    sourceEvent: SessionEvent<*>,
    ciExpRetriedData: List<CiNtfRetriedData>
) :
    Session,
    BaseSessionWithCiNtfExpired(
        sessionWithCiNtfError, ciExpRetriedData, updatedAt = sourceEvent.timestamp) {

    constructor(
        session: BaseSessionWithCiNtfError,
        event: CiNotifyExpiredEvent
    ) : this(sessionWithCiNtfError = session, sourceEvent = event, ciExpRetriedData = emptyList())

    constructor(
        session: BaseSessionWithCiNtfExpired,
        event: CiNtfRetriedEvent
    ) : this(
        sessionWithCiNtfError = session,
        sourceEvent = event,
        ciExpRetriedData = session.ciExpRetriedData + event.data)

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
