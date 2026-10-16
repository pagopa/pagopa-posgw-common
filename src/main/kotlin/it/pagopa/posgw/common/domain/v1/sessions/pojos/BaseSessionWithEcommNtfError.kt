package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithEcommNtfError(
    val sessionWithCiNotified: BaseSessionWithCiNotified,
    // todo add specific data
    updatedAt: Instant
) :
    BaseSessionWithCiNotified(
        sessionWithAuthComp = sessionWithCiNotified.sessionWithAuthComp, updatedAt = updatedAt)
