package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class TransactionActivatedEvent(
    override val sessionId: UUID,
    override val data: TransactionActivatedData
) :
    SessionEvent<TransactionActivatedData>(
        eventCode = SessionEventCode.TRANSACTION_ACTIVATED_EVT, data = data, sessionId = sessionId)
