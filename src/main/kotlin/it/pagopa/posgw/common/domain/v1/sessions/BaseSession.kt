package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant

sealed abstract class BaseSession(
    open val sessionId: SessionId,
    open val correlationId: String,
    open val status: SessionStatus,
    open val createdAt: Instant,
    open val updatedAt: Instant,
)
