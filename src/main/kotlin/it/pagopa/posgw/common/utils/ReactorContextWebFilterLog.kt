package it.pagopa.posgw.common.utils

import io.micrometer.context.ContextRegistry
import it.pagopa.posgw.common.utils.LogTracingUtils.Companion.AttributeKeys
import jakarta.annotation.PostConstruct
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.server.WebFilter
import reactor.core.publisher.Hooks
import reactor.util.context.Context

/**
 * Auto-configuration (registered in META-INF/spring/...AutoConfiguration.imports) that propagates
 * logging attributes from the Reactor context to the SLF4J MDC, so that log lines emitted on any
 * reactive thread carry the same tracing attributes.
 *
 * Enabled by default; set `posgw.tracing.enabled=false` to turn it off.
 */
@AutoConfiguration
// Needs Reactor, micrometer context-propagation and spring-webflux on the application classpath
@ConditionalOnClass(Hooks::class, ContextRegistry::class, WebFilter::class)
// Active only in reactive (WebFlux) applications
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnProperty(
    prefix = "posgw.tracing",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true // Active by default when importing the library
    )
class ReactorContextWebFilterLog {

    // Only these attributes are bridged between the Reactor context and the MDC
    private val contextBound: Set<String> =
        setOf(
            AttributeKeys.EVENT_ACTION.key,
            AttributeKeys.CTX_USER_ID.key,
            AttributeKeys.CTX_TRANSACTION_ID.key,
            AttributeKeys.CORRELATION_ID.key,
            AttributeKeys.CTX_REQUEST_ID.key,
            AttributeKeys.CTX_ORDER_ID.key,
        )

    /**
     * Registers a ThreadLocal accessor per bound key: when a Reactor context value is restored on a
     * thread it is copied into the MDC, and removed when the scope ends. Automatic context
     * propagation makes Reactor apply this on every operator/thread hop.
     */
    @PostConstruct
    fun initMdcMicrometerRegistry() {
        Hooks.enableAutomaticContextPropagation()
        AttributeKeys.entries
            .filter { contextBound.contains(it.key) }
            .forEach { entry ->
                ContextRegistry.getInstance()
                    .registerThreadLocalAccessor(
                        entry.key,
                        { MDC.get(entry.key) },
                        { value: String -> MDC.put(entry.key, value) },
                        { MDC.remove(entry.key) })
            }
    }

    /**
     * Writes EVENT_ACTION ("<HTTP method> <path>") into the Reactor context of every request. An
     * application can replace it by defining its own bean named `reactorContextWebFilter`.
     */
    @Bean
    @ConditionalOnMissingBean(name = ["reactorContextWebFilter"])
    fun reactorContextWebFilter(): WebFilter {
        return WebFilter { exchange, chain ->
            val method = exchange.request.method
            val path = exchange.request.path

            val mdcContext = Context.of(AttributeKeys.EVENT_ACTION.key, "$method $path")

            chain.filter(exchange).contextWrite(mdcContext)
        }
    }
}
