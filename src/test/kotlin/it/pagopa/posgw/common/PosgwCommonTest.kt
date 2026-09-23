package it.pagopa.posgw.common

import kotlin.test.Test
import kotlin.test.assertEquals

class PosgwCommonTest {

    @Test
    fun `should expose library name`() {
        assertEquals("pagopa-posgw-common", PosgwCommon.NAME)
    }
}