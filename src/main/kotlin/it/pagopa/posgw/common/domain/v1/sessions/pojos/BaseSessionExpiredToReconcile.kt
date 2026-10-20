package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.ExpiredData
import java.time.Instant

/**
 * Base data for an expired transaction state that can still be reconciled.
 *
 * The transaction data and the state immediately preceding expiry are retained.
 *
 * @param sessionWithTransactionAct transaction state from which expiry occurred
 * @param data details of the expiry
 * @param updatedAt time the state was created
 */
abstract class BaseSessionExpiredToReconcile(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val data: ExpiredData,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        initializedSession = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt) {
    /** Transaction state immediately preceding expiry. */
    val sessionBeforeExpRecon: BaseSessionWithTransactionAct = sessionWithTransactionAct
}
