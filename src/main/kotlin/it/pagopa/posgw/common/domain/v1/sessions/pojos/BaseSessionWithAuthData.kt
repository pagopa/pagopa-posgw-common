package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import java.time.Instant

abstract class BaseSessionWithAuthComp(
    val sessionWithAuthReq: BaseSessionWithAuthReq,
    val authorizationData: AuthOutcomeReceivedData,
    updatedAt: Instant
) :
    BaseSessionWithAuthReq(
        sessionWithTransactionAct = sessionWithAuthReq.sessionWithTransactionAct,
        authorizationRequestData = sessionWithAuthReq.authorizationRequestData,
        updatedAt = updatedAt)
