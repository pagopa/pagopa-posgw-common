package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class PosAuthorizationRequestErrorEvent(
    override val sessionId: SessionId,
) :
    SessionEvent<Unit>(
        eventCode = SessionEventCode.POS_AUTHORIZATION_REQUEST_ERROR_EVENT,
        data = Unit,
        sessionId = sessionId)
