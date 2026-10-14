package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.documents.v1.sessions.data.PosSessionInitializedData
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class PosSessionInitializedEvent(
    override val sessionId: SessionId,
    override val data: PosSessionInitializedData
) :
    SessionEvent<PosSessionInitializedData>(
        sessionId, SessionEventCode.INITIALIZED, data)
