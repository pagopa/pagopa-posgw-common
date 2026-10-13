package it.pagopa.posgw.common.domain.v1

data class ActivationInfo(
    val paymentInstrumentType: PaymentInstrumentType,
    val rtpId: RtpId,
    val amount: Long,
    val outcome: OutcomeAction,
    val reason: String
)

enum class OutcomeAction(val value: String) {
    OK("OK"),
    KO("KO")
}
