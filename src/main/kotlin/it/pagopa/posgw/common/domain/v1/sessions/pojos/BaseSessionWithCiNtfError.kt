package it.pagopa.posgw.common.domain.v1.sessions.pojos

abstract class BaseSessionWithCiNtfError(
    val sessionWithAuthComp: BaseSessionWithAuthComp,
    // todo add specific data
    updatedAt: java.time.Instant
) :
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthComp.sessionWithAuthReq,
        authorizationData = sessionWithAuthComp.authorizationData,
        updatedAt = updatedAt)
