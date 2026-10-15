package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.PosSessionInitializedData
import java.time.Instant

abstract class BaseSessionInitialized(
    val data: PosSessionInitializedData,
    val session: BaseSession,
    override val updatedAt: Instant,
) :
    BaseSession(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        status = session.status,
        createdAt = session.createdAt,
        updatedAt = updatedAt)
