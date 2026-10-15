package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithEcommNtfExpired(
    val sessionWithEcommNtfError: BaseSessionWithEcommNtfError,
    updatedAt: Instant
) :
    BaseSessionWithEcommNtfError(
        sessionWithCiNotified = sessionWithEcommNtfError.sessionWithCiNotified,
        updatedAt = updatedAt)
