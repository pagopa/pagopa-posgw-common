package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.time.Instant

/**
 * Base data for a transaction-activated state.
 *
 * Session initialization data and the immediate predecessor are retained, including any
 * debt-position data held by that predecessor.
 *
 * @param initializedSession initialized or debt-position-created predecessor
 * @param transactionActivateData transaction activation data
 * @param updatedAt time the transaction-activated state was created
 */
abstract class BaseSessionWithTransactionAct(
    initializedSession: BaseSessionInitialized,
    val transactionActivateData: TransactionActivatedData,
    updatedAt: Instant
) :
    BaseSessionInitialized(
        initializeData = initializedSession.initializeData,
        sessionId = initializedSession.sessionId,
        createdAt = initializedSession.createdAt,
        correlationId = initializedSession.correlationId,
        updatedAt = updatedAt) {
    /** State immediately preceding transaction activation. */
    val sessionBeforeTransactionAct: BaseSessionInitialized = initializedSession
}
