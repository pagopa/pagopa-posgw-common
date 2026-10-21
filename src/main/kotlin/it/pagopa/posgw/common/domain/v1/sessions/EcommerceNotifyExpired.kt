package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfExpired

/**
 * Session state representing an expired eCommerce notification.
 *
 * **Created from:**
 * - [EcommerceNotifyError] via [EcommNtfExpiredEvent]
 * - [EcommerceNotifyExpired] via [EcommNtfRetriedEvent]
 *
 * **Transitions:**
 * - [EcommerceNotified] via [EcommNotifyEvent]
 * - [EcommerceNotifyExpired] via [EcommNtfRetriedEvent]
 *
 * @param sessionWithEcommNtfError failed notification state retained by this state
 * @param sourceEvent event that created or updated this expired state
 * @param ecommNtfExpiredRetriedData retry data recorded after expiry, if any
 */
class EcommerceNotifyExpired
private constructor(
    sessionWithEcommNtfError: BaseSessionWithEcommNtfError,
    sourceEvent: SessionEvent<*>,
    ecommNtfExpiredRetriedData: EcommNtfRetriedData?
) :
    Session,
    BaseSessionWithEcommNtfExpired(
        sessionWithEcommNtfError, ecommNtfExpiredRetriedData, updatedAt = sourceEvent.timestamp) {

    constructor(
        session: BaseSessionWithEcommNtfError,
        ecommNtfExpiredEvent: EcommNtfExpiredEvent
    ) : this(session, sourceEvent = ecommNtfExpiredEvent, ecommNtfExpiredRetriedData = null)

    constructor(
        session: BaseSessionWithEcommNtfError,
        ecommNtfRetriedEvent: EcommNtfRetriedEvent
    ) : this(
        session,
        sourceEvent = ecommNtfRetriedEvent,
        ecommNtfExpiredRetriedData = ecommNtfRetriedEvent.data)

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_EXPIRED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfRetriedEvent -> EcommerceNotifyExpired(this, event)
            else -> this
        }
}
