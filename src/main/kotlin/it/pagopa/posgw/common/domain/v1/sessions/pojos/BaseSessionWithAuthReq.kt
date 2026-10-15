package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.PosAuthorizationRequestedData
import java.time.Instant

abstract class BaseSessionWithAuthReq(
    val sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val posAuthorizationRequestData: PosAuthorizationRequestedData,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt)
