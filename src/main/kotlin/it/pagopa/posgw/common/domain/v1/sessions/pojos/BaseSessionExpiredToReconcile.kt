package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionExpiredToReconcile(
    val sessionWithTransactionAct: BaseSessionWithTransactionAct,
    // todo add specific data
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionInitialized = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt) {}
