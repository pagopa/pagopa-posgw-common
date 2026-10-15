package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthorizationOutcomeReceivedData
import java.time.Instant

abstract class BaseSessionWithAuthComp(
    val sessionWithAuthReq: BaseSessionWithAuthReq,
    val authorizationData: AuthorizationOutcomeReceivedData,
    updatedAt: Instant
) :
    BaseSessionWithAuthReq(
        sessionWithTransactionAct = sessionWithAuthReq.sessionWithTransactionAct,
        posAuthorizationRequestData = sessionWithAuthReq.posAuthorizationRequestData,
        updatedAt = updatedAt)
