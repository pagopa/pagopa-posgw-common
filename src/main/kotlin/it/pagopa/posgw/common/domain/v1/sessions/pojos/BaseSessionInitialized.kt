package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.SessionInitializedData
import it.pagopa.posgw.common.domain.v1.CorrelationId
import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

/**
 * Session data established at initialization and carried into later states.
 *
 * @param initializeData initialization payload
 * @param sessionId identifier assigned to the session
 * @param createdAt time the session was initialized
 * @param correlationId identifier used to correlate related operations
 * @param updatedAt time of the latest event
 */
abstract class BaseSessionInitialized(
    val initializeData: SessionInitializedData,
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
