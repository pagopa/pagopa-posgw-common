package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.*
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfError

/**
 * Session state representing a failed eCommerce notification.
 *
 * Retry events update the retry data; an expiry event moves the session to an expired state.
 *
 * **Created from:**
 * - [CiNotified] via [EcommNtfErrorEvent]
 * - [EcommerceNotifyError] via [EcommNtfRetriedEvent]
 *
 * **Transitions:**
 * - [EcommerceNotified] via [EcommNotifyEvent]
 * - [EcommerceNotifyError] via [EcommNtfRetriedEvent]
 * - [EcommerceNotifyExpired] via [EcommNtfExpiredEvent]
 *
 * @param sessionWithCiNotified creditor-institution-notified state retained by this state
 * @param sourceEvent event that created or updated this error state
 * @param ecommNotifyErrorData data describing the notification failure
 * @param ecommNotifyRetriedData latest retry data, or `null` before a retry
 */
class EcommerceNotifyError
private constructor(
    sessionWithCiNotified: BaseSessionWithCiNotified,
    sourceEvent: SessionEvent<*>,
    ecommNotifyErrorData: EcommNtfErrorData,
    ecommNotifyRetriedData: List<EcommNtfRetriedData>
) :
    Session,
    BaseSessionWithEcommNtfError(
        sessionWithCiNotified,
        ecommNotifyErrorData,
        ecommNotifyRetriedData,
        sourceEvent.timestamp) {

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_ERROR

    constructor(
        session: BaseSessionWithCiNotified,
        event: EcommNtfErrorEvent
    ) : this(
        sessionWithCiNotified = session,
        sourceEvent = event,
        ecommNotifyErrorData = event.data,
        ecommNotifyRetriedData = emptyList())

    constructor(
        session: BaseSessionWithEcommNtfError,
        event: EcommNtfRetriedEvent
    ) : this(
        sessionWithCiNotified = session,
        sourceEvent = event,
        ecommNotifyErrorData = session.ecommNotifyErrorData,
        ecommNotifyRetriedData = session.ecommNotifyRetriedData + event.data)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfRetriedEvent -> EcommerceNotifyError(this, event)
            is EcommNtfExpiredEvent -> EcommerceNotifyExpired(this, event)
            else -> this
        }
}
