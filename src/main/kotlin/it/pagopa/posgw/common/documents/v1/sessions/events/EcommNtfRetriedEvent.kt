package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class EcommNtfRetriedEvent(
    override val sessionId: UUID,
    override val data: EcommNtfRetriedData
) :
    SessionEvent<EcommNtfRetriedData>(
        eventCode = SessionEventCode.ECOMM_NTF_RETRIED_EVT, data = data, sessionId = sessionId)
