package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Unique identifier assigned by the pagoPA Nodo to a transaction. It follows the rules defined in
 * the
 * [pagoPA Nodo](https://github.com/pagopa/pagopa-api/blob/024d0ebb3a29c252f632a8ca8c8f4acedeabda0d/xsd-common/sac-common-types-1.0.xsd#L16-L21).
 * Each notice code maps to a single payment token.
 *
 * @param value Unique transaction identifier assigned by the pagoPA Nodo.
 */
@ValueObject
@JvmInline
value class PaymentToken(val value: String) {
    init {
        require(value.isNotBlank() && value.length <= 35) {
            "PaymentToken cannot be blank and must be at most 35 characters long"
        }
    }
}
