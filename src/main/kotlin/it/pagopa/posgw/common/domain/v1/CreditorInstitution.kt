package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.Entity
import it.pagopa.posgw.common.annotation.ValueObject

/**
 * A creditor institution, identified by its fiscal code.
 *
 * @property fiscalCode Fiscal code identifying the institution.
 * @property logicalOffice Logical office associated with the institution.
 * @property workStation Workstation associated with the institution.
 */
@Entity
data class CreditorInstitution(
    val fiscalCode: FiscalCode,
    val logicalOffice: LogicalOffice,
    val workStation: WorkStation,
) {

    /**
     * Identifies a logical office of a creditor institution.
     *
     * @param value Logical office identifier.
     */
    @ValueObject
    @JvmInline
    value class LogicalOffice(val value: String) {
        init {
            require(value.isNotBlank()) { "Logical office cannot be blank" }
        }
    }

    /**
     * Creditor institution fiscal code.
     *
     * @param value Fiscal code value.
     */
    @ValueObject
    @JvmInline
    value class FiscalCode(val value: String) {
        init {
            require(value.isNotBlank()) { "Fiscal code cannot be blank" }
        }
    }

    /**
     * Identifies a workstation.
     *
     * @param value Workstation identifier.
     */
    @ValueObject
    @JvmInline
    value class WorkStation(val value: String) {
        init {
            require(value.isNotBlank()) { "Workstation cannot be blank" }
        }
    }
}
