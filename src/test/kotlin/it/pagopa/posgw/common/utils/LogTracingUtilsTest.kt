package it.pagopa.posgw.common.utils

import it.pagopa.posgw.common.utils.LogTracingUtils.Companion.AttributeKeys
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.slf4j.Logger
import org.slf4j.MDC
import reactor.util.context.Context

class LogTracingUtilsTest {

    @AfterEach
    fun clearMdc() {
        MDC.clear()
    }

    @Test
    fun `logInfo writes attributes details and success outcome then cleans them up`() {
        val capturingLogger = loggerCapturingMdc()

        LogTracingUtils.loggerTracingUtils()
            .attributes(mapOf(AttributeKeys.CTX_TRANSACTION_ID to "transaction-123"))
            .details(mapOf("payment_method" to "card"))
            .dependency(LogTracingUtils.GPD_DEPENDENCY)
            .success()
            .logInfo(capturingLogger.logger, "Payment method handled")

        assertEquals("transaction-123", capturingLogger.mdc["ctx_transaction_id"])
        assertEquals(
            """{"payment_method":"card","dependency":"gpd-service"}""",
            capturingLogger.mdc["ctx_details"],
        )
        assertEquals("success", capturingLogger.mdc["event_outcome"])
        assertNull(MDC.get("ctx_transaction_id"))
        assertNull(MDC.get("ctx_details"))
        assertNull(MDC.get("event_outcome"))
    }

    @Test
    fun `logDebug and logWarn emit their messages`() {
        val logger = mock<Logger>()
        val events = mutableListOf<String>()
        doAnswer {
                events.add("debug:${it.getArgument<String>(0)}")
                null
            }
            .whenever(logger)
            .debug(any())
        doAnswer {
                events.add("warn:${it.getArgument<String>(0)}")
                null
            }
            .whenever(logger)
            .warn(any())

        LogTracingUtils.loggerTracingUtils().logDebug(logger, "debug message")
        LogTracingUtils.loggerTracingUtils().logWarn(logger, "warning message")

        assertEquals(listOf("debug:debug message", "warn:warning message"), events)
    }

    @Test
    fun `logError includes throwable type and failure outcome`() {
        val capturingLogger = loggerCapturingMdc()
        val error = IllegalStateException("dependency failed")

        LogTracingUtils.loggerTracingUtils()
            .failure()
            .logError(capturingLogger.logger, error, "Request failed")

        assertEquals("failure", capturingLogger.mdc["event_outcome"])
        assertEquals(IllegalStateException::class.java.name, capturingLogger.mdc["error.type"])
        assertFalse(capturingLogger.mdc.containsKey("error.stack_trace"))
        assertNull(MDC.get("event_outcome"))
        assertNull(MDC.get("error.type"))
    }

    @Test
    fun `logError without throwable logs a failure outcome`() {
        val capturingLogger = loggerCapturingMdc()

        LogTracingUtils.loggerTracingUtils()
            .failure()
            .logError(capturingLogger.logger, "Request failed")

        assertEquals("failure", capturingLogger.mdc["event_outcome"])
        assertFalse(capturingLogger.mdc.containsKey("error.type"))
    }

    @Test
    fun `logWithStackTrace includes error type and stack trace`() {
        val capturingLogger = loggerCapturingMdc()
        val error = IllegalArgumentException("invalid request")

        LogTracingUtils.loggerTracingUtils()
            .logWithStackTrace(capturingLogger.logger, error, "Request failed")

        assertEquals(IllegalArgumentException::class.java.name, capturingLogger.mdc["error.type"])
        assertEquals(error.stackTraceToString(), capturingLogger.mdc["error.stack_trace"])
        assertEquals("{eventOutcome-not-found}", capturingLogger.mdc["event_outcome"])
    }

    @Test
    fun `enrichContext supports an attribute and a map of attributes`() {
        LogTracingUtils.enrichContextMDC(AttributeKeys.CTX_REQUEST_ID, "request-123")
        LogTracingUtils.enrichContextMDC(
            mapOf(
                AttributeKeys.CORRELATION_ID to "correlation-123",
                AttributeKeys.PSP_ID to "psp-123",
            ))

        assertEquals("request-123", MDC.get("ctx_request_id"))
        assertEquals("correlation-123", MDC.get("correlation_id"))
        assertEquals("psp-123", MDC.get("psp_id"))
    }

    @Test
    fun `enrichContext uses the attribute default when a single value is null`() {
        LogTracingUtils.enrichContextMDC(AttributeKeys.CTX_ORDER_ID, null)

        assertEquals("{orderId-not-found}", MDC.get("ctx_order_id"))
    }

    @Test
    fun `enrichContext uses each attribute default when a map value is null`() {
        LogTracingUtils.enrichContextMDC(
            mapOf(
                AttributeKeys.CTX_ORDER_ID to null, AttributeKeys.CTX_REQUEST_ID to "request-123"))

        assertEquals("{orderId-not-found}", MDC.get("ctx_order_id"))
        assertEquals("request-123", MDC.get("ctx_request_id"))
    }

    @Test
    fun `enrichContext adds attributes to the returned reactor context`() {
        val enriched =
            LogTracingUtils.enrichContextMDC(
                mapOf(
                    AttributeKeys.CORRELATION_ID to "correlation-123",
                    AttributeKeys.PSP_ID to "psp-123",
                ),
                Context.empty(),
            )

        assertEquals("correlation-123", enriched.get<String>("correlation_id"))
        assertEquals("psp-123", enriched.get<String>("psp_id"))
    }

    @Test
    fun `enrichContext keeps existing reactor context entries and does not mutate the input`() {
        val original = Context.of("existing", "value")

        val enriched =
            LogTracingUtils.enrichContextMDC(mapOf(AttributeKeys.PSP_ID to "psp-123"), original)

        assertEquals("value", enriched.get<String>("existing"))
        assertEquals("psp-123", enriched.get<String>("psp_id"))
        assertFalse(original.hasKey("psp_id"))
    }

    @Test
    fun `enrichContext uses the attribute default in the reactor context when a value is null`() {
        val enriched =
            LogTracingUtils.enrichContextMDC(
                mapOf(
                    AttributeKeys.CTX_ORDER_ID to null,
                    AttributeKeys.CTX_REQUEST_ID to "request-123"),
                Context.empty(),
            )

        assertEquals("{orderId-not-found}", enriched.get<String>("ctx_order_id"))
        assertEquals("request-123", enriched.get<String>("ctx_request_id"))
    }

    @Test
    fun `enrichContext returns the context unchanged for an empty attribute map`() {
        val original = Context.of("existing", "value")

        val enriched = LogTracingUtils.enrichContextMDC(emptyMap(), original)

        assertEquals(1, enriched.size())
        assertEquals("value", enriched.get<String>("existing"))
    }

    @Test
    fun `enrichContext on the reactor context does not write to the MDC`() {
        LogTracingUtils.enrichContextMDC(mapOf(AttributeKeys.PSP_ID to "psp-123"), Context.empty())

        assertNull(MDC.get("psp_id"))
    }

    @Test
    fun `MDC keys are cleaned up when logger throws`() {
        val logger = mock<Logger>()
        doAnswer { throw IllegalStateException("logger failed") }.whenever(logger).info(any())

        assertThrows(IllegalStateException::class.java) {
            LogTracingUtils.loggerTracingUtils()
                .attributes(mapOf(AttributeKeys.CTX_TRANSACTION_ID to "transaction-123"))
                .details(mapOf("payment_method" to "card"))
                .success()
                .logInfo(logger, "Payment method handled")
        }

        assertNull(MDC.get("ctx_transaction_id"))
        assertNull(MDC.get("ctx_details"))
        assertNull(MDC.get("event_outcome"))
    }

    private fun loggerCapturingMdc(): CapturingLogger {
        val logger = mock<Logger>()
        val capturedMdc = mutableMapOf<String, String>()
        doAnswer {
                capturedMdc.clear()
                capturedMdc.putAll(MDC.getCopyOfContextMap().orEmpty())
                null
            }
            .whenever(logger)
            .info(any())
        doAnswer {
                capturedMdc.clear()
                capturedMdc.putAll(MDC.getCopyOfContextMap().orEmpty())
                null
            }
            .whenever(logger)
            .error(any())

        return CapturingLogger(logger, capturedMdc)
    }

    private data class CapturingLogger(val logger: Logger, val mdc: Map<String, String>)
}
