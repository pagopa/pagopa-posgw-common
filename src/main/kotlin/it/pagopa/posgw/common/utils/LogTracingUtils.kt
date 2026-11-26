package it.pagopa.posgw.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.util.EnumMap
import org.slf4j.Logger
import org.slf4j.MDC
import org.slf4j.event.Level
import reactor.util.context.Context

/**
 * Utility class for structured logging utilizing the Fluent Builder pattern.
 *
 * This class facilitates the population of the SLF4J Mapped Diagnostic Context (MDC) with
 * predefined attributes, custom details, and error information. It ensures that MDC keys are safely
 * added before logging and properly cleaned up immediately after the log is emitted, preventing
 * context leaks in concurrent environments.
 */
class LogTracingUtils private constructor() {

    private var message: String = ""
    private var outcome: String = AttributeKeysPrivate.EVENT_OUTCOME.defaultValue
    private var stackTrace: String? = null
    private var error: Throwable? = null
    private val attributes: EnumMap<AttributeKeys, String> = EnumMap(AttributeKeys::class.java)
    private var details: MutableMap<String, String> = mutableMapOf()
    private val mdcKeys = mutableMapOf<String, String?>()
    private var logger: Logger? = null

    companion object {

        private val objectMapper =
            ObjectMapper()
                .registerModule(JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        /**
         * Creates a new instance of the log builder.
         *
         * @return a new {@link LogTracingUtils} instance
         */
        fun loggerTracingUtils(): LogTracingUtils = LogTracingUtils()

        /**
         * Serializes the custom details map into a JSON string.
         *
         * @param details the map to serialize
         * @return a JSON formatted string, or "{}" if serialization fails or map is null
         */
        private fun serializeDetailsToMdcMap(details: Map<String, *>): String {
            val rawDetails = "{}"
            if (details.isNotEmpty()) {
                try {
                    return objectMapper.writeValueAsString(details)
                } catch (e: Exception) {
                    // Log the error and return an empty JSON object
                    return rawDetails
                }
            }
            return rawDetails
        }

        /**
         * Adds attributes to the SLF4J MDC. Null values are replaced with their configured
         * defaults.
         *
         * @param attributes map of standard {@link AttributeKeys} and their values
         */
        fun enrichContextMDC(attributes: Map<AttributeKeys, String?>) {
            for (attribute in attributes) {
                MDC.put(attribute.key.key, attribute.value ?: attribute.key.defaultValue)
            }
        }

        /**
         * Adds an attribute to the SLF4J MDC. A null value is replaced with the key's configured
         * default.
         *
         * @param key the standard {@link AttributeKeys} to set
         * @param value the attribute value, or null to use the key's default
         */
        fun enrichContextMDC(key: AttributeKeys, value: String?) {
            MDC.put(key.key, value ?: key.defaultValue)
        }

        /**
         * Adds attributes to a Reactor [Context]. Null values are replaced with their configured
         * defaults.
         *
         * @param attributes map of standard {@link AttributeKeys} and their values
         * @param context the context to enrich
         * @return a new [Context] containing the attributes (Reactor contexts are immutable)
         */
        fun enrichContextMDC(attributes: Map<AttributeKeys, String?>, context: Context): Context =
            attributes.entries.fold(context) { ctx, (key, value) ->
                ctx.put(key.key, value ?: key.defaultValue)
            }

        /**
         * Adds an attribute to the Reactor [Context]. A null value is replaced with the key's
         * configured default.
         *
         * @param key the standard {@link AttributeKeys} to set
         * @param value the attribute value, or null to use the key's default
         * @param context the context to enrich
         * @return a new [Context] containing the attribute (Reactor contexts are immutable)
         */
        fun enrichContextMDC(key: AttributeKeys, value: String?, context: Context): Context {
            return context.put(key.key, value ?: key.defaultValue)
        }

        const val SUCCESS: String = "success"

        const val FAILURE: String = "failure"

        /** External service dependency value used in MDC for GPD service operations. */

        /** Dependency value used in MDC for GPD service operations. */
        const val GPD_DEPENDENCY: String = "gpd-service"

        /** Dependency value used in MDC for ecommerce service operations. */
        const val ECOMMERCE_DEPENDENCY: String = "ecommerce-service"

        /** Dependency value used in MDC for GEC service operations. */
        const val GEC_DEPENDENCY: String = "gec-service"

        /** Internal service dependency value used in MDC for GPD service operations. */

        /** Dependency value used in MDC for MongoDB operations. */
        const val MONGO_DEPENDENCY: String = "posGateway-mongodb"

        /** Dependency value used in MDC for Redis operations. */
        const val REDIS_DEPENDENCY: String = "posGateway-redis"

        /** Dependency value used in MDC for GESTIONALE_INTERFACE_POSGTW service operations. */
        const val GESTIONALE_INTERFACE_POSGTW: String = "gestionale-interface-posgtw-service"

        /** Dependency value used in MDC for AUTHORIZER_POSGTW service operations. */
        const val AUTHORIZER_POSGTW: String = "authorizer-posgtw-service"

        /** Dependency value used in MDC for POS_INTERFACE_POSGTW service operations. */
        const val POS_INTERFACE_POSGTW: String = "pos-interface-posgtw-service"

        /** Dependency value used in MDC for TRANSACTION_HANDLER_POSGTW service operations. */
        const val TRANSACTION_HANDLER_POSGTW_DEPENDENCY: String =
            "transaction-handler-posgtw-service"

        /** Dependency value used in MDC for DP_HANDLER_POSGTW service operations. */
        const val DP_HANDLER_POSGTW_DEPENDENCY: String = "dp-handler-posgtw-service"

        /** Dependency value used in MDC for RETRY_HANDLER_POSGTW service operations. */
        const val RETRY_HANDLER_POSGTW_DEPENDENCY: String = "retry-handler-posgtw-service"

        /** Dependency value used in MDC for WATCHDOG_POSGTW service operations. */
        const val WATCHDOG_POSGTW_DEPENDENCY: String = "watchdog-posgtw-service"

        /** Enumeration of standard public keys used for MDC and Context population. */
        enum class AttributeKeys(val key: String, defaultValue: String) {
            /** Context key for action associated with the event. */
            EVENT_ACTION("event_action", "{eventAction-not-found}"),

            /** Context key for transaction identifier. */
            CTX_TRANSACTION_ID("ctx_transaction_id", "{transactionId-not-found}"),

            /** Context key for authorization request identifier. */
            CTX_AUTHORIZATION_REQUEST_ID(
                "ctx_authorization_request_id",
                "{authorizationRequestId-not-found}",
            ),

            /** Context key for event code. */
            CTX_EVENT_CODE("ctx_event_code", "{eventCode-not-found}"),

            /** Context key for event identifier. */
            CTX_EVENT_ID("ctx_event_id", "{eventId-not-found}"),

            /** Context key for RPT identifiers. */
            CTX_RPT_IDS("ctx_rpt_ids", "{rptIds-not-found}"),

            /** Context key for payment tokens. */
            CTX_PAYMENT_TOKENS("ctx_payment_tokens", "{paymentTokens-not-found}"),

            /** Context key for user identifier. */
            CTX_USER_ID("ctx_user_id", "{userId-not-found}"),

            /** MDC key for correlation identifier. */
            CORRELATION_ID("correlation_id", "{correlationId-not-found}"),

            /** MDC key for PSP identifier. */
            PSP_ID("psp_id", "{pspId-not-found}"),

            /** Context key for wallet id. */
            CTX_WALLET_ID("ctx_wallet_id", "{walletId-not-found}"),

            /** Context key for client id. */
            CTX_CLIENT_ID("ctx_client_id", "{clientId-not-found}"),

            /** Context key for request id. */
            CTX_REQUEST_ID("ctx_request_id", "{requestId-not-found}"),

            /** Context key for order id. */
            CTX_ORDER_ID("ctx_order_id", "{orderId-not-found}"),

            /** Context key for session id. */
            CTX_SESSION_ID("ctx_session_id", "{sessionId-not-found}");

            val defaultValue: String

            init {
                this.defaultValue = defaultValue
            }
        }

        /** Enumeration of internal/private MDC keys handled exclusively by the builder. */
        private enum class AttributeKeysPrivate(val key: String, defaultValue: String) {
            /** MDC key for custom JSON details map. */
            CTX_DETAILS("ctx_details", "{details-not-found}"),

            /** MDC key for event outcome. */
            EVENT_OUTCOME("event_outcome", "{eventOutcome-not-found}"),

            /**
             * Details key for dependency name involved in the operation (serialized inside
             * ctx.details).
             */
            DEPENDENCY("dependency", "{dependency-not-found}"),

            /** MDC key for error class name. */
            ERROR_TYPE("error.type", "{errorType-not-found}"),

            /** MDC key for error message text. */
            ERROR_MESSAGE("error.message", "{errorMessage-not-found}"),

            /** MDC key for the complete error stack trace. */
            ERROR_STACK_TRACE("error.stack_trace", "{errorStackTrace-not-found}");

            val defaultValue: String

            init {
                this.defaultValue = defaultValue
            }
        }
    }

    /**
     * Sets a map of standard attributes to be added to the MDC.
     *
     * @param attributes map of standard {@link AttributeKeys} and their values
     * @return this builder instance
     */
    fun attributes(attributes: Map<AttributeKeys, String>): LogTracingUtils {
        this.attributes.putAll(attributes)
        return this
    }

    /**
     * Adds custom details that will be serialized as a JSON string in the MDC.
     *
     * @param details a map containing custom key-value string pairs
     * @return this builder instance
     */
    fun details(details: Map<String, String>): LogTracingUtils {
        this.details.putAll(details)
        return this
    }

    /**
     * Sets the external dependency name involved in the current operation.
     *
     * @param dependency the name of the dependency
     * @return this builder instance
     */
    fun dependency(dependency: String): LogTracingUtils {
        this.details[AttributeKeysPrivate.DEPENDENCY.key] = dependency
        return this
    }

    /**
     * Marks the outcome of the logged event as successful.
     *
     * @return this builder instance
     */
    fun success(): LogTracingUtils {
        this.outcome = SUCCESS
        return this
    }

    /**
     * Marks the outcome of the logged event as a failure.
     *
     * @return this builder instance
     */
    fun failure(): LogTracingUtils {
        this.outcome = FAILURE
        return this
    }

    /**
     * Adds a key-value pair to the MDC for the duration of a single log call, saving the value the
     * key had before (null if it was not set) so it can be restored after logging.
     *
     * @param key the MDC key
     * @param value the MDC value
     */
    private fun addMdcKey(key: String, value: String?) {
        // Save the previous MDC value only the first time the key is written, so a repeated write
        // cannot overwrite the original with this builder's own value. A null entry means the key
        // was not set before. Values propagated through enrichContextMDC are therefore overridden
        // only for this log and restored afterward.
        if (key !in mdcKeys) {
            this.mdcKeys[key] = MDC.get(key)
        }
        MDC.put(key, value)
    }

    /**
     * Core logging routine. Populates the MDC with attributes, details, and errors, issues the log
     * statement using the requested level, and immediately performs MDC cleanup to prevent context
     * leaks.
     *
     * @param loggerLevel the SLF4J log level
     */
    private fun log(loggerLevel: Level) {
        // Add attributes keys and values to MDC map
        if (attributes.isNotEmpty()) {
            attributes.forEach { (key, value) -> run { addMdcKey(key.key, value) } }
        }

        // Add details key and value to MDC map
        if (this.details.isNotEmpty()) {
            addMdcKey(AttributeKeysPrivate.CTX_DETAILS.key, serializeDetailsToMdcMap(this.details))
        }

        addMdcKey(AttributeKeysPrivate.EVENT_OUTCOME.key, this.outcome)

        this.error?.let { error ->
            addMdcKey(AttributeKeysPrivate.ERROR_TYPE.key, error.javaClass.name)
        }

        this.error?.message?.let { errorMessage ->
            addMdcKey(AttributeKeysPrivate.ERROR_MESSAGE.key, errorMessage)
        }

        this.stackTrace?.let { stackTrace ->
            addMdcKey(AttributeKeysPrivate.ERROR_STACK_TRACE.key, stackTrace)
        }

        try {
            when (loggerLevel) {
                Level.INFO -> logger?.info(this.message)
                Level.ERROR -> logger?.error(this.message)
                Level.WARN -> logger?.warn(this.message)
                Level.DEBUG -> logger?.debug(this.message)
                Level.TRACE -> logger?.trace(this.message)
            }
        } finally {
            // Restore the MDC to its state before this log: put back the previous value of keys
            // that were already set (e.g. propagated by enrichContextMDC) and remove the keys
            // that this builder added.
            this.mdcKeys.forEach { (key, value) ->
                if (value == null) MDC.remove(key) else MDC.put(key, value)
            }
            this.mdcKeys.clear()
        }
    }

    fun logInfo(logger: Logger, message: String) {
        this.message = message
        this.logger = logger
        log(Level.INFO)
    }

    fun logDebug(logger: Logger, message: String) {
        this.message = message
        this.logger = logger
        log(Level.DEBUG)
    }

    fun logWarn(logger: Logger, message: String) {
        this.message = message
        this.logger = logger
        log(Level.WARN)
    }

    fun logError(logger: Logger, throwable: Throwable?, message: String) {
        this.message = message
        this.logger = logger
        this.error = throwable
        log(Level.ERROR)
    }

    fun logError(logger: Logger, message: String) {
        this.message = message
        this.logger = logger
        logError(logger, null, message)
    }

    fun logWithStackTrace(logger: Logger, throwable: Throwable, message: String) {
        this.message = message
        this.logger = logger
        this.error = throwable
        this.stackTrace = throwable.stackTraceToString()
        log(Level.ERROR)
    }
}
