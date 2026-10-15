package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant

abstract class BaseSession(
    open val sessionId: SessionId,
    open val correlationId: String,
    open val status: SessionStatus,
    open val createdAt: Instant,
    open val updatedAt: Instant,
)
