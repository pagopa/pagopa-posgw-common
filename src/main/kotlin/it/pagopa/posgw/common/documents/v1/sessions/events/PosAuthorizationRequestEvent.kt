package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.domain.v1.SessionId
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class PosAuthorizationRequestEvent(
    override val sessionId: SessionId,
    override val data: AuthRequestedData
) :
    SessionEvent<AuthRequestedData>(
        eventCode = SessionEventCode.POS_AUTHORIZATION_REQUESTED_EVENT,
        data = data,
        sessionId = sessionId)
