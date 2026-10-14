package it.pagopa.posgw.common.domain.v1

data class ActivationInfo(
    val paymentInstrumentType: PaymentInstrumentType,
    val rptId: RptId,
    val amount: Long,
    val outcome: Outcome,
    val reason: String
)

enum class Outcome(val value: String) {
    OK("OK"),
    KO("KO")
}
