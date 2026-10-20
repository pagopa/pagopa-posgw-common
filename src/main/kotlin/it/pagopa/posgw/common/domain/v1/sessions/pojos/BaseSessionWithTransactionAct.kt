package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.time.Instant

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
        updatedAt = updatedAt) {}
