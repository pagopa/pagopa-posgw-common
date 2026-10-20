package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfError
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfExpired
import java.time.Instant

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
 * @param ecommNtfExpiredRetriedData retry data recorded after expiry, if any
 * @param updatedAt time of the latest session event
 */
class EcommerceNotifyExpired(
    sessionWithEcommNtfError: BaseSessionWithEcommNtfError,
    ecommNtfExpiredRetriedData: EcommNtfRetriedData? = null,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithEcommNtfExpired(
        sessionWithEcommNtfError, ecommNtfExpiredRetriedData, updatedAt = updatedAt) {

    constructor(
        session: BaseSessionWithEcommNtfError,
        ecommNtfExpiredEvent: EcommNtfExpiredEvent
    ) : this(session, updatedAt = ecommNtfExpiredEvent.timestamp)

    constructor(
        session: BaseSessionWithEcommNtfError,
        ecommNtfRetriedEvent: EcommNtfRetriedEvent
    ) : this(session, ecommNtfRetriedEvent.data, ecommNtfRetriedEvent.timestamp)

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_EXPIRED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfRetriedEvent -> EcommerceNotifyExpired(this, event)
            else -> this
        }
}
