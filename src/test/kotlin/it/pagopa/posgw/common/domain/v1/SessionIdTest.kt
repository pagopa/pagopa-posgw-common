package it.pagopa.posgw.common.domain.v1

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class SessionIdTest {
    @Test
    fun `preserves a supplied uuid`() {
        val uuid = UUID.fromString("3a95a8f1-9c25-4f0f-83cc-9d94aaf89b8f")

        assertEquals(uuid, SessionId(uuid).id)
    }

    @Test
    fun `generates a version four uuid when omitted`() {
        val generatedId = SessionId().id

        assertEquals(4, generatedId.version())
        assertEquals(2, generatedId.variant())
    }
}
