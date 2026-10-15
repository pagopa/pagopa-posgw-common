package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Identifies a payment channel.
 *
 * @param value Channel identifier.
 */
@ValueObject @JvmInline value class ChannelId(val value: String)
