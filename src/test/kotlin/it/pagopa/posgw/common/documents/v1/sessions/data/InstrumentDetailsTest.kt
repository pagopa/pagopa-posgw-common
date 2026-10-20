package it.pagopa.posgw.common.documents.v1.sessions.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class InstrumentDetailsTest {
    @Test
    fun `card details are available through the sealed base type`() {
        val details: InstrumentDetails = CardDetails("VISA", "1234", "411111")

        assertEquals("CARD", details.instrumentType)
        assertIs<CardDetails>(details)
    }
}
