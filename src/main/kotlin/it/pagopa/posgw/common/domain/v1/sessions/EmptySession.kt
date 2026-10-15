package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.PosSessionInitializedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent

class EmptySession : Session {
    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is PosSessionInitializedEvent -> Initialized(event)
            else -> this
        }
}
