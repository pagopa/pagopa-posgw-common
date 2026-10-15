package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import java.time.Instant

abstract class BaseSessionError(
    sessionInitialized: BaseSessionInitialized,
    errorData: ErrorData,
    override val updatedAt: Instant
) :
    BaseSessionInitialized(
        sessionId = sessionInitialized.sessionId,
        initializeData = sessionInitialized.initializeData,
        createdAt = sessionInitialized.createdAt,
        correlationId = sessionInitialized.correlationId,
        updatedAt = updatedAt)
