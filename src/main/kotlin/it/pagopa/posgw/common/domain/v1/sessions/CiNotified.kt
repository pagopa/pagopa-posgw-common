package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.CiNotifiedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithAuthComp
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import java.time.Instant

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
 * @param sessionWithAuthComp authorization-completed state
 * @param updatedAt time of the creditor-institution notification event
 */
class CiNotified(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    updatedAt: Instant,
) : Session, BaseSessionWithCiNotified(sessionWithAuthComp, updatedAt = updatedAt) {

    override val status: SessionStatus = SessionStatus.CI_NOTIFIED

    constructor(
        session: BaseSessionWithAuthComp,
        event: CiNotifiedEvent
    ) : this(sessionWithAuthComp = session, updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfErrorEvent -> EcommerceNotifyError(this, event)
            else -> this
        }
}
