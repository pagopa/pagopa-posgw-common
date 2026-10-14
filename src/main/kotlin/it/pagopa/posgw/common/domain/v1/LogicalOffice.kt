package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Identifies a logical office of a creditor institution.
 *
 * @param value Logical office identifier.
 */
@ValueObject class LogicalOffice(val value: String)
