package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionInitializedEvent

/**
 * Initial session state with no predecessor.
 *
 * **Created directly.**
 *
 * **Transitions:**
 * - [Initialized] via [SessionInitializedEvent]
 */
class EmptySession : Session {
    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is SessionInitializedEvent -> Initialized(event)
            else -> this
        }
}
