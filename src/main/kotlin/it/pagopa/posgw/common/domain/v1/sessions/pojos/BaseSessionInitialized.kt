package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.PosSessionInitializedData
import it.pagopa.posgw.common.domain.v1.CorrelationId
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

abstract class BaseSessionInitialized(
    val initializeData: PosSessionInitializedData,
    sessionId: SessionId,
    createdAt: Instant,
    correlationId: CorrelationId,
    updatedAt: Instant,
) :
    BaseSession(
        sessionId = sessionId,
        correlationId = correlationId,
        createdAt = createdAt,
        updatedAt = updatedAt)
