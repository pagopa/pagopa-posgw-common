package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionWithEcommNotified(
    sessionWithCiNotified: BaseSessionWithCiNotified,
    updatedAt: Instant
) : BaseSessionWithCiNotified(sessionWithAuthComp = sessionWithCiNotified, updatedAt = updatedAt)
