package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * The identifier of the PSP sender entity's Channel. Reference:
 * [channelId](https://github.com/pagopa/pagopa-api/blob/024d0ebb3a29c252f632a8ca8c8f4acedeabda0d/openapi/fdr_psp.json#L2464)
 *
 * @param value Channel identifier.
 */
@ValueObject
@JvmInline
value class ChannelId(val value: String) {
    init {
        require(value.isNotBlank() && value.length <= 35) {
            "ChannelId cannot be blank and must be at most 35 characters long"
        }
    }
}
