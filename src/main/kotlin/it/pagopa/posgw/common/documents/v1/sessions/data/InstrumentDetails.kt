package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.PaymentInstrumentType

sealed class InstrumentDetails(val instrumentType: String)

data class CardDetails(val brand: String, val last4Digits: String, val bin: String) :
    InstrumentDetails(PaymentInstrumentType.CARD.name)
