package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData

abstract class BaseSessionExpired(
    val sessionInitialized: BaseSessionInitialized,
    val expiredData: ExpiredData,
    override val updatedAt: java.time.Instant
) :
    BaseSessionInitialized(
        sessionId = sessionInitialized.sessionId,
        initializeData = sessionInitialized.initializeData,
        createdAt = sessionInitialized.createdAt,
        correlationId = sessionInitialized.correlationId,
        updatedAt = updatedAt)
