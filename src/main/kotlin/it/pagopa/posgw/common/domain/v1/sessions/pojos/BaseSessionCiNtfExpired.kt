package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionCiNtfExpired(
    val sessionWithCiNtfError: BaseSessionWithCiNtfError,
    // todo add specific data
    updatedAt: Instant
) :
    BaseSessionWithCiNtfError(
        sessionWithAuthComp = sessionWithCiNtfError.sessionWithAuthComp, updatedAt = updatedAt) {}
