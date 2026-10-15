package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant

abstract class BaseSession(
    val sessionId: SessionId,
    val correlationId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    abstract val status: SessionStatus
}
