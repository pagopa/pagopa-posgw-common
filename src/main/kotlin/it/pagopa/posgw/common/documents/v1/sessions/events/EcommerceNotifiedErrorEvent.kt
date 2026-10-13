package it.pagopa.posgw.common.documents.v1.sessions.events

import it.pagopa.posgw.common.domain.v1.SessionId
import it.pagopa.posgw.common.domain.v1.sessions.SessionEventCode
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
data class EcommerceNotifiedErrorEvent(
    override val sessionId: SessionId,
) :
    SessionEvent<Unit>(
        eventCode = SessionEventCode.ECOMMERCE_AUTHORIZATION_OUTCOME_NOTIFY_ERROR.code,
        data = Unit,
        sessionId = sessionId)
