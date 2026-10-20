package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

abstract class BaseSessionReconciled(
    sessionToReconcile: BaseSessionExpiredToReconcile,
    updatedAt: Instant
) :
    BaseSessionExpiredToReconcile(
        sessionToReconcile, sessionToReconcile.data, updatedAt = updatedAt)
