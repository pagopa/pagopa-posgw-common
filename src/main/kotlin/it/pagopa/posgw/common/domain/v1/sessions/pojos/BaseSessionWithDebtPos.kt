package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import java.time.Instant

abstract class BaseSessionWithDebtPos(
    open val sessionInitialized: BaseSessionInitialized,
    open val debtPositionData: DebtPositionCreatedData,
    override val updatedAt: Instant
) :
    BaseSessionInitialized(
        sessionId = sessionInitialized.sessionId,
        initializeData = sessionInitialized.initializeData,
        createdAt = sessionInitialized.createdAt,
        correlationId = sessionInitialized.correlationId,
        updatedAt = updatedAt)
