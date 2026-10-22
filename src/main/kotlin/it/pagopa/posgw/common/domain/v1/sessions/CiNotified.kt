package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified

/**
 * Session state reached after the creditor institution has been notified.
 *
 * The authorization data and predecessor state are retained for subsequent notifications.
 *
 * **Created from:** [AuthorizationCompleted], [CiNotifyError], or [CiNotifyExpired] via
 * [CiNotifiedEvent].
 *
 * **Transitions:**
 * - [EcommerceNotified] via [EcommNotifyEvent]
 * - [EcommerceNotifyError] via [EcommNtfErrorEvent]
 *
 * @param session authorization-completed state
 * @param event creditor-institution notification event
 */
class CiNotified(session: BaseSessionWithAuthComp, event: CiNotifiedEvent) :
    Session, BaseSessionWithCiNotified(session, updatedAt = event.timestamp) {

    override val status: SessionStatus = SessionStatus.CI_NOTIFIED

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfErrorEvent -> EcommerceNotifyError(this, event)
            else -> this
        }
}
