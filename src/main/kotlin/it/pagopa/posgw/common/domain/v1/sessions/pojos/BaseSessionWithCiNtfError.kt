package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData

abstract class BaseSessionWithCiNtfError(
    val sessionWithAuthComp: BaseSessionWithAuthComp,
    val CiNtfErrorData: CiNtfErrorData,
    updatedAt: java.time.Instant
) :
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthComp.sessionWithAuthReq,
        authorizationData = sessionWithAuthComp.authorizationData,
        updatedAt = updatedAt)
