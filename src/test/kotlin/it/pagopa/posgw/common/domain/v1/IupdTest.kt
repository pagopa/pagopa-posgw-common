package it.pagopa.posgw.common.domain.v1

import kotlin.test.Test
import kotlin.test.assertEquals

class IupdTest {
    @Test
    fun `exposes its value`() {
        assertEquals("iupd-123", Iupd("iupd-123").value)
    }
}
