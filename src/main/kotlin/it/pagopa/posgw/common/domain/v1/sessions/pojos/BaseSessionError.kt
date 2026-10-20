package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import java.time.Instant

/**
 * Base data for an error state, preserving the initialized session metadata.
 *
 * @param sessionInitialized initialized state whose metadata is retained
 * @param errorData details of the session error
 * @param updatedAt time the error state was created
 */
abstract class BaseSessionError(
    sessionInitialized: BaseSessionInitialized,
    val errorData: ErrorData,
    updatedAt: Instant
) :
    BaseSessionInitialized(
        sessionId = sessionInitialized.sessionId,
        initializeData = sessionInitialized.initializeData,
        createdAt = sessionInitialized.createdAt,
        correlationId = sessionInitialized.correlationId,
        updatedAt = updatedAt)
