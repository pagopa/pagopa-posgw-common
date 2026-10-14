package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.ActivationInfo

data class TransactionActivatedData(
    val transactionId: String,
    val activationInfo: List<ActivationInfo>
)
