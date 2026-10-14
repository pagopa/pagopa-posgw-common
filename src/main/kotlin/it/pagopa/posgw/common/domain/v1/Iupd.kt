package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Unique identifier assigned to a debt position.
 *
 * @param value IUPD identifier.
 */
@ValueObject data class Iupd(val value: String)
