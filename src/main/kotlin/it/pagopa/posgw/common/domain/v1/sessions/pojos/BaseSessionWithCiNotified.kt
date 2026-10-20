package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithCiNotified(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    updatedAt: Instant
) :
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthComp,
        authorizationData = sessionWithAuthComp.authorizationData,
        updatedAt = updatedAt) {
    val sessionBeforeCiNtf: BaseSessionWithAuthComp = sessionWithAuthComp
}
