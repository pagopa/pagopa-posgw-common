package it.pagopa.posgw.common.documents.v1.sessions.events

import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.springframework.data.mongodb.core.mapping.Document

class SessionEventTest {
    @Test
    fun `creates events with a UUID id and current timestamp`() {
        val sessionId = UUID.fromString("a2f44c4e-b866-4d20-a5df-27c373194c5a")
        val before = Instant.now()
        val event = CiNotifiedEvent(sessionId)
        val after = Instant.now()

        assertNotNull(UUID.fromString(event.id))
        assertTrue(event.timestamp >= before && event.timestamp <= after)
        assertTrue(
            SessionEvent::class.java.isAnnotationPresent(Document::class.java),
        )
    }
}
