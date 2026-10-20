package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData

/**
 * Base data for a terminal expired state, preserving the initialized session metadata.
 *
 * @param previewSession initialized state whose metadata is retained
 * @param expiredData details of the expiry
 * @param updatedAt time the expired state was created
 */
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
