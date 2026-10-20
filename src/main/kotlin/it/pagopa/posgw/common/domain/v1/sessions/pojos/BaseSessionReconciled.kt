package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

/**
 * Base data for a reconciled state, retaining the expired transaction data.
 *
 * @param sessionToReconcile expired state being reconciled
 * @param updatedAt time the reconciliation state was created
 */
abstract class BaseSessionReconciled(
    sessionToReconcile: BaseSessionExpiredToReconcile,
    updatedAt: Instant
) :
    BaseSessionExpiredToReconcile(
        sessionToReconcile, sessionToReconcile.data, updatedAt = updatedAt)
