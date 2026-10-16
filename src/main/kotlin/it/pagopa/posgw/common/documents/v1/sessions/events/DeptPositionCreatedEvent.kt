package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class DeptPositionCreatedEvent(
    override val sessionId: UUID,
    override val data: DebtPositionCreatedData
) :
    SessionEvent<DebtPositionCreatedData>(
        sessionId = sessionId, eventCode = SessionEventCode.DEBT_POSITION_CREATED_EVT, data = data)
