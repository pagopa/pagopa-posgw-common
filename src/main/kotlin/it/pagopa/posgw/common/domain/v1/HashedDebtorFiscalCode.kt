package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Obfuscated fiscal code of a debtor.
 *
 * @param value Obfuscated debtor fiscal code.
 */
@ValueObject
@JvmInline
value class HashedDebtorFiscalCode(val value: String) {
    init {
        require(value.isNotBlank()) { "Hashed debtor fiscal code cannot be blank" }
    }
}
