package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import java.time.Instant

abstract class BaseSessionExpiredToReconcile(
    val sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val data: ExpiredData,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionInitialized = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt) {}
