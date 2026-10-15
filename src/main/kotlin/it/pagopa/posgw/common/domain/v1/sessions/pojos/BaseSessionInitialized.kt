package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.PosSessionInitializedData
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

abstract class BaseSessionInitialized(
    open val initializeData: PosSessionInitializedData,
    override val sessionId: SessionId,
    override val createdAt: Instant,
    override val correlationId: String,
    override val updatedAt: Instant,
) :
    BaseSession(
        sessionId = sessionId,
        correlationId = correlationId,
        createdAt = createdAt,
        updatedAt = updatedAt)
