package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionReconciled(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    updatedAt: Instant
) : BaseSessionExpiredToReconcile(sessionWithTransactionAct, updatedAt = updatedAt)
