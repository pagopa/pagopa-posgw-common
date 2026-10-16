package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithCiNotified(
    val sessionWithAuthComp: BaseSessionWithAuthComp,
    updatedAt: Instant
) :
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthComp.sessionWithAuthReq,
        authorizationData = sessionWithAuthComp.authorizationData,
        updatedAt = updatedAt)
