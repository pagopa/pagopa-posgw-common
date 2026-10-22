package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Transaction identifier and payment activation results for a session.
 *
 * @param transactionId identifier assigned to the transaction
 * @param activationInfo activation result for each payment notice in the transaction
 */
data class TransactionActivatedData(
    val transactionId: String,
    val activationInfo: List<ActivationInfo>
) {
    /**
     * Activation result for one payment notice.
     *
     * @param paymentToken token used to identify the payment instrument
     * @param rptId payment notice identifier
     * @param amount amount associated with the payment notice
     * @param outcome outcome returned by the activation operation
     * @param reason optional explanation for the activation outcome
     */
    data class ActivationInfo(
        val paymentToken: String,
        val rptId: String,
        val amount: Long,
        val outcome: String,
        val reason: String?
    )
}
