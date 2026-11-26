package it.pagopa.posgw.common.utils

import it.pagopa.posgw.common.utils.LogTracingUtils.Companion.AttributeKeys
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import reactor.core.publisher.Mono

class ReactorContextWebFilterLogTests {

    private val autoConfig = AutoConfigurations.of(ReactorContextWebFilterLog::class.java)

    private val reactiveRunner = ReactiveWebApplicationContextRunner().withConfiguration(autoConfig)

    @Test
    fun `filter writes EVENT_ACTION into the reactor context`() {
        val captured = AtomicReference<String?>()
        val exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/sessions/1").build())

        val filter = ReactorContextWebFilterLog().reactorContextWebFilter()
        filter
            .filter(exchange) {
                Mono.deferContextual { ctx ->
                    captured.set(ctx.get<String>(AttributeKeys.EVENT_ACTION.key))
                    Mono.empty()
                }
            }
            .block()

        assertEquals("GET /sessions/1", captured.get())
    }

    @Test
    fun `bean is registered in a reactive web application`() {
        reactiveRunner.run { ctx -> assert(ctx.containsBean("reactorContextWebFilter")) }
    }

    @Test
    fun `bean is not registered when disabled by property`() {
        reactiveRunner.withPropertyValues("posgw.tracing.enabled=false").run { ctx ->
            assert(!ctx.containsBean("reactorContextWebFilter"))
        }
    }
}
