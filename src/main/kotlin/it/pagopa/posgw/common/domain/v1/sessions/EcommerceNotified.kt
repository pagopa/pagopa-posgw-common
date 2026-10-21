package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNotified

/**
 * Session state reached after the eCommerce notification succeeds.
 *
 * **Created from:** [CiNotified], [EcommerceNotifyError], or [EcommerceNotifyExpired] via
 * [EcommNotifyEvent].
 *
 * **Transitions:** None; later events leave this state unchanged.
 *
 * @param session creditor-institution-notified state retained by this state
 * @param event successful eCommerce notification event
 */
class EcommerceNotified(session: BaseSessionWithCiNotified, event: EcommNotifyEvent) :
    Session, BaseSessionWithEcommNotified(session, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.ECOMM_NOTIFIED

    override fun applyEvent(event: SessionEvent<*>): Session {
        return this
    }
}
