package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPositionCreatedData
import java.time.Instant

abstract class BaseSessionWithDebtPos(
    val sessionInitialized: BaseSessionInitialized,
    val debtPositionData: DebtPositionCreatedData,
    override val updatedAt: Instant
) :
    BaseSessionInitialized(
        session = sessionInitialized, data = sessionInitialized.data, updatedAt = updatedAt)
