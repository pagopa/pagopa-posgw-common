package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.*
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNtfError

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
 * @param sourceEvent event that created or updated this error state
 * @param ciNtfErrorData data describing the notification failure
 * @param ciNtfRetriedData latest retry data, or `null` before a retry
 */
class CiNotifyError
private constructor(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    sourceEvent: SessionEvent<*>,
    ciNtfErrorData: CiNtfErrorData,
    ciNtfRetriedData: List<CiNtfRetriedData>
) :
    Session,
    BaseSessionWithCiNtfError(
        sessionWithAuthComp, ciNtfErrorData, ciNtfRetriedData, sourceEvent.timestamp) {

    override val status: SessionStatus = SessionStatus.CI_NTF_ERROR

    constructor(
        session: BaseSessionWithAuthComp,
        event: CiNtfErrorEvent
    ) : this(
        sessionWithAuthComp = session,
        sourceEvent = event,
        ciNtfErrorData = event.data,
        ciNtfRetriedData = emptyList())

    constructor(
        session: BaseSessionWithCiNtfError,
        event: CiNtfRetriedEvent
    ) : this(
        sessionWithAuthComp = session,
        sourceEvent = event,
        ciNtfErrorData = session.ciNtfErrorData,
        ciNtfRetriedData = session.ciNtfRetriedData + event.data)

    override fun applyEvent(event: SessionEvent<*>): Session {
        return when (event) {
            is CiNotifiedEvent -> CiNotified(this, event)
            is CiNtfRetriedEvent -> CiNotifyError(this, event)
            is CiNotifyExpiredEvent -> CiNotifyExpired(this, event)
            else -> this
        }
    }
}
