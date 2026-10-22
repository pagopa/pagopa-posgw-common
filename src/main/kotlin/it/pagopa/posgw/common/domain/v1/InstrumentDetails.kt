package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Describes the payment instrument used for a transaction.
 *
 * @property type Kind of payment instrument.
 */
@ValueObject
sealed class InstrumentDetails(open val type: Type) {

    /**
     * Details of a card payment instrument.
     *
     * @property brand Card brand.
     * @property last4Digits Last four digits of the card number.
     * @property bin Bank identification number (BIN) of the card.
     * @property type Payment instrument type, always [Type.CARD].
     */
    @ValueObject
    data class CardInstrumentDetails(
        val brand: String,
        val last4Digits: String,
        val bin: String,
    ) : InstrumentDetails(type = Type.CARD) {
        init {
            require(last4Digits.length == 4 && last4Digits.all { it.isDigit() }) {
                "Last 4 digits must be exactly 4 digits"
            }
            require(bin.length in 6..8 && bin.all { it.isDigit() }) {
                "BIN must be between 6 and 8 digits"
            }
        }
    }

    /** Supported kinds of payment instruments. */
    enum class Type {
        CARD
    }
}
