package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Authorization code associated with the transaction.
 *
 * @param value Authorization code value.
 */
@ValueObject @JvmInline value class AuthorizationCode(val value: String)
