package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import java.time.Instant

abstract class BaseSessionExpiredToReconcile(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val data: ExpiredData,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        initializedSession = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt) {
    val sessionBeforeExpRecon: BaseSessionWithTransactionAct = sessionWithTransactionAct
}
