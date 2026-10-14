package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

@ValueObject
data class InstrumentDetails(
    val type: PaymentInstrumentType,
    val brand: CardType,
    val last4Digits: String,
    val bin: String,
)

@ValueObject
data class CardInstrumentDetails(
    val brand: CardType,
    val last4Digits: String,
    val bin: String,
) {
    val type: PaymentInstrumentType = PaymentInstrumentType.CARD
}

enum class PaymentInstrumentType(var value: String) {
    CARD("CARD")
}

enum class CardType(var value: String) {
    VISA("VISA"),
    MASTERCARD("MASTERCARD"),
    DINERS("DINERS"),
    AMEX("AMEX"),
    MAESTRO("MAESTRO")
}
