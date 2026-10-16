package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.time.Instant

abstract class BaseSessionWithTransactionAct(
    val sessionInitialized: BaseSessionInitialized,
    val transactionActivateData: TransactionActivatedData,
    updatedAt: Instant
) :
    BaseSessionInitialized(
        initializeData = sessionInitialized.initializeData,
        sessionId = sessionInitialized.sessionId,
        createdAt = sessionInitialized.createdAt,
        correlationId = sessionInitialized.correlationId,
        updatedAt = updatedAt) {

    /** The session at its previous state before the transaction was activated. */
    val sessionAtPreviousState: BaseSessionInitialized = sessionInitialized
}
