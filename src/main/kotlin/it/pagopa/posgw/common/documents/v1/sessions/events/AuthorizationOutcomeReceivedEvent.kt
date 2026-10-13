package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthorizationOutcomeReceivedData
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class AuthorizationOutcomeReceivedEvent(
    override val sessionId: SessionId,
    override val data: AuthorizationOutcomeReceivedData
) :
    SessionEvent<AuthorizationOutcomeReceivedData>(
        eventCode = SessionEventCode.AUTHORIZATION_OUTCOME_RECEIVED.code,
        data = data,
        sessionId = sessionId)
