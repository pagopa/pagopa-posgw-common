package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifyExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.CiNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfError
import java.time.Instant

/**
 * Session state representing a failed creditor-institution notification.
 *
 * Retry events update the retry data; an expiry event moves the session to an expired state.
 *
 * **Created from:**
 * - [AuthorizationCompleted] via [CiNtfErrorEvent]
 * - [CiNotifyError] via [CiNtfRetriedEvent]
 *
 * **Transitions:**
 * - [CiNotified] via [CiNotifiedEvent]
 * - [CiNotifyError] via [CiNtfRetriedEvent]
 * - [CiNotifyExpired] via [CiNotifyExpiredEvent]
 *
 * @param sessionWithAuthComp authorization-completed state retained by this state
 * @param ciNtfErrorData data describing the notification failure
 * @param ciNtfRetriedData latest retry data, or `null` before a retry
 * @param updatedAt time of the latest session event
 */
class CiNotifyError(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    ciNtfErrorData: CiNtfErrorData,
    ciNtfRetriedData: CiNtfRetriedData? = null,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithCiNtfError(sessionWithAuthComp, ciNtfErrorData, ciNtfRetriedData, updatedAt) {

    override val status: SessionStatus = SessionStatus.CI_NTF_ERROR

    constructor(
        session: BaseSessionWithAuthComp,
        event: CiNtfErrorEvent
    ) : this(
        sessionWithAuthComp = session, ciNtfErrorData = event.data, updatedAt = event.timestamp)

    constructor(
        session: BaseSessionWithCiNtfError,
        event: CiNtfRetriedEvent
    ) : this(
        sessionWithAuthComp = session,
        ciNtfErrorData = session.ciNtfErrorData,
        ciNtfRetriedData = event.data,
        updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session {
        return when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            is CiNtfRetriedEvent -> CiNotifyError(this, event)
            is CiNotifyExpiredEvent -> CiNotifyExpired(this, event)
            else -> this
        }
    }
}
