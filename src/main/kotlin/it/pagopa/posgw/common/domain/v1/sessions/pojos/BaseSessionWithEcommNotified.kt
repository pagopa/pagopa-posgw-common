package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithEcommNotified(
    val sessionWithCiNotified: BaseSessionWithCiNotified,
    override val updatedAt: Instant
) :
    BaseSessionWithCiNotified(
        sessionWithAuthComp = sessionWithCiNotified.sessionWithAuthComp, updatedAt = updatedAt)
