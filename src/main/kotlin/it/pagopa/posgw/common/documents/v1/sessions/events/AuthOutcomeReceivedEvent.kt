package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class AuthOutcomeReceivedEvent(
    override val sessionId: UUID,
    override val data: AuthOutcomeReceivedData
) :
    SessionEvent<AuthOutcomeReceivedData>(
        eventCode = SessionEventCode.AUTH_OUTCOME_RECEIVED_EVT, data = data, sessionId = sessionId)
