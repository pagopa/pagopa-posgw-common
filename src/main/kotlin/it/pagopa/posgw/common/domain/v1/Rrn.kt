package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Retrieval Reference Number associated with a transaction.
 *
 * @param value Retrieval Reference Number.
 */
@ValueObject
@JvmInline
value class Rrn(val value: String) {
    init {
        require(value.isNotBlank()) { "RRN cannot be blank" }
    }
}
