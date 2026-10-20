package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData

abstract class BaseSessionExpired(
    previewSession: BaseSessionInitialized,
    val expiredData: ExpiredData,
    updatedAt: java.time.Instant
) :
    BaseSessionInitialized(
        sessionId = previewSession.sessionId,
        initializeData = previewSession.initializeData,
        createdAt = previewSession.createdAt,
        correlationId = previewSession.correlationId,
        updatedAt = updatedAt)
