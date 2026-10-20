package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import java.time.Instant

abstract class BaseSessionWithAuthError(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val authRequestErrorData: AuthRequestErrorData,
    val authRequestRetriedData: AuthRequestRetriedData?,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        initializedSession = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt)
