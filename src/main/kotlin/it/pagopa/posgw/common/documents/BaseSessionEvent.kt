package it.pagopa.posgw.common.documents

import it.pagopa.posgw.common.domain.v1.SessionId
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "eventstore")
abstract class BaseSessionEvent<T>(
    @Id val id: String,
    open val timestamp: Instant,
    open val sessionId: SessionId,
    open val eventCode: String,
    open val data: T
) {
    /*
     * Two events are considered equal if they have the same id, timestamp, sessionId, eventCode and data.
     */
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseSessionEvent<*>) return false

        if (id != other.id) return false
        if (sessionId != other.sessionId) return false
        if (eventCode != other.eventCode) return false
        if (data != other.data) return false

        return true
    }

    final override fun hashCode(): Int = id.hashCode()
}
