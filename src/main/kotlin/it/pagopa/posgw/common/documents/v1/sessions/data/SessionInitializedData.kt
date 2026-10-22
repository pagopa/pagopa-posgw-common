package it.pagopa.posgw.common.documents.v1.sessions.data
/**
 * Data captured when a payment session is initialized.
 *
 * @param psp payment service provider selected for the session
 * @param creditorInstitution creditor institution associated with the payment notice
 * @param terminalIds POS terminal identifiers available to the session
 * @param correlationId identifier used to correlate related operations
 */
data class SessionInitializedData(
    val psp: Psp,
    val creditorInstitution: CreditorInstitution,
    val terminalIds: List<String>,
    val correlationId: String
) {
    /**
     * Creditor-institution identifiers associated with a payment session.
     *
     * @param fiscalCode creditor institution fiscal code
     * @param logicalOffice logical office identifier
     * @param workStation workstation identifier
     */
    data class CreditorInstitution(
        val fiscalCode: String,
        val logicalOffice: String,
        val workStation: String,
    )
}
