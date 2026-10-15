package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class TransactionActivatedEvent(
    override val sessionId: SessionId,
    override val data: TransactionActivatedData
) :
    SessionEvent<TransactionActivatedData>(
        eventCode = SessionEventCode.TRANSACTION_ACTIVATED_EVENT,
        data = data,
        sessionId = sessionId)
