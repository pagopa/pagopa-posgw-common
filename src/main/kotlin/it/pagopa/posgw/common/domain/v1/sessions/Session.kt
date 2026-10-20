package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent

/** A session state that can be advanced by applying an event. */
interface Session {
    /**
     * Applies an event and returns the resulting state.
     *
     * Implementations return the same instance for events that are not valid in the current state.
     *
     * @param event event to apply
     * @return the next state, or this state if the event is ignored
     */
    fun applyEvent(event: SessionEvent<*>): Session
}
