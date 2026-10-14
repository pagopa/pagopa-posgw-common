package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Fiscal code used to identify a person or institution.
 *
 * @param value Fiscal code value.
 */
@ValueObject
@JvmInline
value class FiscalCode(val value: String) {
    // TODO : add init section for validation of fiscal code format
}
