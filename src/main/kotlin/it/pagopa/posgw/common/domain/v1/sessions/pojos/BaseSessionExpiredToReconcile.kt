package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionExpiredToReconcile(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    // todo add specific data
    override val updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionWithDebtPos = sessionWithTransactionAct.sessionWithDebtPos,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt) {}
