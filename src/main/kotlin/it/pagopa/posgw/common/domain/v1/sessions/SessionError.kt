package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.events.ErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.ErrorType
import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.Steps
import java.time.Instant

class SessionError(
    override val sessionId: SessionId,
    override val correlationId: String,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    val step: Steps,
    val timestamp: Instant,
    val errorType: ErrorType,
    val errorCode: String,
    val errorMessage: String
) :
    Session,
    BaseSession(
        sessionId = sessionId,
        correlationId = correlationId,
        status = SessionStatus.ERROR,
        createdAt = createdAt,
        updatedAt = Instant.now()) {

    constructor(
        session: BaseSession,
        event: ErrorEvent
    ) : this(
        sessionId = session.sessionId,
        correlationId = session.correlationId,
        createdAt = session.createdAt,
        updatedAt = Instant.now(),
        step = event.data.step,
        timestamp = event.data.timestamp,
        errorType = event.data.errorType,
        errorCode = event.data.errorCode,
        errorMessage = event.data.errorMessage)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            else -> this
        }
}
