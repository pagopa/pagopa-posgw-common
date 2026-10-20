package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Debt-position identifiers retained in a payment session.
 *
 * @param iupd unique identifier assigned to the debt position
 * @param rptId payment notice identifier associated with the debt position
 * @param hashedDebtorFiscalCode hashed fiscal code of the debtor
 */
data class DebtPosition(val iupd: String, val rptId: String, val hashedDebtorFiscalCode: String)
