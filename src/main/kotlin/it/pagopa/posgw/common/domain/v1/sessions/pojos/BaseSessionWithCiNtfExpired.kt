package it.pagopa.posgw.common.domain.v1.sessions.pojos

abstract class BaseSessionWithCiNtfExpired(
    val sessionWithCiNtfError: BaseSessionWithCiNtfError,
    // todo add specific data
    updatedAt: java.time.Instant
) :
    BaseSessionWithCiNtfError(
        sessionWithAuthComp = sessionWithCiNtfError.sessionWithAuthComp, updatedAt = updatedAt)
