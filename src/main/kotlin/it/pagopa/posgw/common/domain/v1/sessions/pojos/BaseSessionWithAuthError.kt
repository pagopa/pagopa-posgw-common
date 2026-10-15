package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithAuthError(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    // todo add specific data
    override val updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionWithDebtPos = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt)
