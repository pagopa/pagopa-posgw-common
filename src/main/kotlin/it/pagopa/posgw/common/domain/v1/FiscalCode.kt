package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

@ValueObject
@JvmInline
value class FiscalCode(val value: String) {
    // TODO : add init section for validation of fiscal code format
}
