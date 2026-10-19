package it.pagopa.posgw.common.documents.v1.sessions.events

import java.util.UUID
import kotlin.test.assertEquals
import org.springframework.data.mongodb.core.mapping.Document

internal fun <T> assertSessionEvent(
    event: SessionEvent<T>,
    sessionId: UUID,
    expectedCode: SessionEventCode,
    expectedData: T,
) {
    assertEquals(sessionId, event.sessionId)
    assertEquals(expectedCode.name, event.eventCode)
    assertEquals(expectedData, event.data)
    assertEquals("eventstore", event.javaClass.getAnnotation(Document::class.java).collection)
}
