package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import java.time.Instant

/**
 * Base data for a debt-position-created state.
 *
 * @param sessionInitialized initialized state whose metadata is retained
 * @param debtPositionData created debt-position data
 * @param updatedAt time the debt-position state was created
 */
abstract class BaseSessionWithDebtPos(
    sessionInitialized: BaseSessionInitialized,
    val debtPositionData: DebtPositionCreatedData,
    updatedAt: Instant
) :
    BaseSessionInitialized(
        sessionId = sessionInitialized.sessionId,
        initializeData = sessionInitialized.initializeData,
        createdAt = sessionInitialized.createdAt,
        correlationId = sessionInitialized.correlationId,
        updatedAt = updatedAt)
