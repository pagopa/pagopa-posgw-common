package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.PosAuthorizationRequestedData
import java.time.Instant

abstract class BaseSessionWithAuthReq(
    val sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val posAuthorizationRequestData: PosAuthorizationRequestedData,
    override val updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionWithDebtPos = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt)
