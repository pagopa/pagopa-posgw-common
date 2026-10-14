package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

@ValueObject
data class ActivationInfo(
    val paymentToken: PaymentToken,
    val rptId: RptId,
    val amount: Long,
    val outcome: Outcome,
    val reason: String? = null
)

enum class Outcome(val value: String) {
    OK("OK"),
    KO("KO")
}
