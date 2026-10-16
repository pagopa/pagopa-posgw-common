package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.PaymentInstrumentType

data class AuthOutcomeReceivedData(
    val authDetails: AuthDetails,
    val instrumentDetails: InstrumentDetails
) {

    data class AuthDetails(val outcome: String, val rrn: String, val authorizationCode: String)

    sealed class InstrumentDetails(val instrumentType: String)

    data class CardDetails(val brand: String, val last4Digits: String, val bin: String) :
        InstrumentDetails(PaymentInstrumentType.CARD.name)
}
