package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent

class SessionAuthorizationOutcomeNotified : Session {
    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            else -> this
        }
}
