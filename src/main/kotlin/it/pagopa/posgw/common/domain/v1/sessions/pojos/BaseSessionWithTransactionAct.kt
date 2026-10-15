package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.time.Instant

abstract class BaseSessionWithTransactionAct(
    val sessionWithDebtPos: BaseSessionWithDebtPos,
    val transactionActivateData: TransactionActivatedData,
    override val updatedAt: Instant
) :
    BaseSessionWithDebtPos(
        sessionInitialized = sessionWithDebtPos.sessionInitialized,
        debtPositionData = sessionWithDebtPos.debtPositionData,
        updatedAt = updatedAt)
