package it.pagopa.posgw.common.domain.v1

/**
 * Payment obligation identified by its IUPD and associated RPT identifier.
 *
 * @property iupd Unique identifier of the debt position.
 * @property rptId Identifier formed from the creditor institution's fiscal code and notice code.
 * @property debtorFiscalCode Obfuscated fiscal code of the debtor.
 */
data class DebtPosition(val iupd: Iupd, val rptId: RptId, val debtorFiscalCode: DebtorFiscalCode)
