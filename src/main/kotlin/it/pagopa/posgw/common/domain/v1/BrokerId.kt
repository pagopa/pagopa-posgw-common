package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Identifies a payment service provider's broker.
 *
 * @param value Broker identifier.
 */
@ValueObject @JvmInline value class BrokerId(val value: String)
