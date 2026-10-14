package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Unique identifier assigned by the pagoPA Nodo to a transaction.
 *
 * Each notice code maps to a single payment token.
 *
 * @param value Unique transaction identifier assigned by the pagoPA Nodo.
 */
@ValueObject @JvmInline value class PaymentToken(val value: String)
