package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class EcommNtfErrorEvent(
    override val sessionId: UUID,
    override val data: EcommNtfErrorData,
) :
    SessionEvent<EcommNtfErrorData>(
        sessionId = sessionId, eventCode = SessionEventCode.ECOMM_NTF_ERROR_EVT, data = data)
