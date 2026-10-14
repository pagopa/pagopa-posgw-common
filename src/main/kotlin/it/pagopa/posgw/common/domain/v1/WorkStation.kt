package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Identifies a workstation.
 *
 * @param value Workstation identifier.
 */
@ValueObject @JvmInline value class WorkStation(val value: String)
