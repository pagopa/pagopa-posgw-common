package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import java.time.Instant

abstract class BaseSessionWithAuthReq(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val authorizationRequestData: AuthRequestedData,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt)
