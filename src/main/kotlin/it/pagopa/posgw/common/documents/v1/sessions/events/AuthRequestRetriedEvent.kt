package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Records a retry of a failed authorization request.
 *
 * @param sessionId identifier of the session
 * @param data retry count and error details
 */
@Document(collection = "eventstore")
data class AuthRequestRetriedEvent(
    override val sessionId: UUID,
    override val data: AuthRequestRetriedData
) :
    SessionEvent<AuthRequestRetriedData>(
        eventCode = SessionEventCode.AUTH_REQUEST_RETRIED_EVT, data = data, sessionId = sessionId)
