package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Details specific to the payment instrument used in an authorization.
 *
 * @param instrumentType name of the payment-instrument type
 */
sealed class InstrumentDetails(val instrumentType: Type) {

    /**
     * Card attributes returned for an authorization.
     *
     * @param brand card network or brand
     * @param last4Digits last four digits of the card number
     * @param bin bank identification number for the card
     */
    data class CardDetails(val brand: String, val last4Digits: String, val bin: String) :
        InstrumentDetails(Type.CARD)

    enum class Type {
        CARD
    }
}
