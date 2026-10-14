package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class DeptPositionCreatedEvent(
    override val sessionId: SessionId,
    override val data: DebtPositionCreatedData
) :
    SessionEvent<DebtPositionCreatedData>(
        sessionId = sessionId, eventCode = SessionEventCode.DEBT_POSITION_CREATED, data = data)
