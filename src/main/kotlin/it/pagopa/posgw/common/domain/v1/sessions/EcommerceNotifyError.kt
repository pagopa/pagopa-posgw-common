package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfError
import java.time.Instant

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
 * @param ecommNotifyErrorData data describing the notification failure
 * @param ecommNotifyRetriedData latest retry data, or `null` before a retry
 * @param updatedAt time of the latest session event
 */
class EcommerceNotifyError(
    sessionWithCiNotified: BaseSessionWithCiNotified,
    ecommNotifyErrorData: EcommNtfErrorData,
    ecommNotifyRetriedData: EcommNtfRetriedData? = null,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithEcommNtfError(
        sessionWithCiNotified, ecommNotifyErrorData, ecommNotifyRetriedData, updatedAt) {

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_ERROR

    constructor(
        session: BaseSessionWithCiNotified,
        event: EcommNtfErrorEvent
    ) : this(
        sessionWithCiNotified = session,
        ecommNotifyErrorData = event.data,
        updatedAt = event.timestamp)

    constructor(
        session: BaseSessionWithEcommNtfError,
        event: EcommNtfRetriedEvent
    ) : this(
        sessionWithCiNotified = session,
        ecommNotifyErrorData = session.ecommNotifyErrorData,
        ecommNotifyRetriedData = event.data,
        updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfRetriedEvent -> EcommerceNotifyError(this, event)
            is EcommNtfExpiredEvent -> EcommerceNotifyExpired(this, event)
            else -> this
        }
}
