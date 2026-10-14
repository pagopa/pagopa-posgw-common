package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Describes the payment instrument used for a transaction.
 *
 * @property type Kind of payment instrument.
 * @property brand Card brand.
 * @property last4Digits Last four digits of the card number.
 * @property bin Bank identification number (BIN) of the card.
 */
@ValueObject
sealed class InstrumentDetails(
    open val type: PaymentInstrumentType,
    open val brand: CardType,
    open val last4Digits: String,
    open val bin: String,
)

/**
 * Details of a card payment instrument.
 *
 * @property brand Card brand.
 * @property last4Digits Last four digits of the card number.
 * @property bin Bank identification number (BIN) of the card.
 * @property type Payment instrument type, always [PaymentInstrumentType.CARD].
 */
@ValueObject
data class CardInstrumentDetails(
    override val brand: CardType,
    override val last4Digits: String,
    override val bin: String,
) : InstrumentDetails(
    type = PaymentInstrumentType.CARD,
    brand = brand,
    last4Digits = last4Digits,
    bin = bin
)

/**
 * Supported kinds of payment instruments.
 *
 * @property value String representation of the instrument type.
 */
enum class PaymentInstrumentType(var value: String) {
    CARD("CARD")
}

/**
 * Card brands supported by the payment gateway.
 *
 * @property value String representation of the card brand.
 */
enum class CardType(var value: String) {
    VISA("VISA"),
    MASTERCARD("MASTERCARD"),
    DINERS("DINERS"),
    AMEX("AMEX"),
    MAESTRO("MAESTRO")
}
