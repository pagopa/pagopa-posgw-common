package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.SessionInitializedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records initialization of a payment session.
 *
 * @param sessionId identifier assigned to the session
 * @param data session initialization details
 */
@Document(collection = "eventstore")
data class SessionInitializedEvent(
    override val sessionId: UUID,
    override val data: SessionInitializedData
) : SessionEvent<SessionInitializedData>(sessionId, SessionEventCode.SESSION_INITIALIZED_EVT, data)
