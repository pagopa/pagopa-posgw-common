package it.pagopa.posgw.common.documents.v1.sessions.data


data class TransactionActivatedData(
    val transactionId: String,
    val activationInfo: List<ActivationInfo>
){
    data class ActivationInfo(
        val paymentToken: String,
        val rptId: String,
        val amount: Long,
        val outcome: String,
        val reason: String?
    )
}
