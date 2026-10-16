package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * The domain identifier of the PSP sender entity's Broker. Reference:
 * [brokerId](https://github.com/pagopa/pagopa-api/blob/024d0ebb3a29c252f632a8ca8c8f4acedeabda0d/openapi/fdr_psp.json#L2456)
 *
 * @param value Broker identifier.
 */
@ValueObject
@JvmInline
value class BrokerId(val value: String) {
    init {
        require(value.isNotBlank() && value.length <= 35) {
            "BrokerId cannot be blank and must be at most 35 characters long"
        }
    }
}
