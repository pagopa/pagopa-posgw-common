package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.domain.v1.CorrelationId
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant

/**
 * Shared identity and timing data retained by every session state.
 *
 * @param sessionId identifier assigned to the session
 * @param correlationId identifier used to correlate related operations
 * @param createdAt time the session was initialized
 * @param updatedAt time of the event that produced this state
 */
abstract class BaseSession(
    val sessionId: SessionId,
    val correlationId: CorrelationId,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    /** Lifecycle status represented by this session state. */
    abstract val status: SessionStatus
}
