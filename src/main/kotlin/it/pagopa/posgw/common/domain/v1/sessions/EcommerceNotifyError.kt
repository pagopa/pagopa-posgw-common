package it.pagopa.posgw.common.domain.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNotifyEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfErrorEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfExpiredEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.EcommNtfRetriedEvent
import it.pagopa.posgw.common.documents.v1.sessions.events.SessionEvent
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithCiNotified
import it.pagopa.posgw.common.domain.v1.sessions.pojos.BaseSessionWithEcommNtfError
import java.time.Instant

class EcommerceNotifyError(
    sessionWithCiNotified: BaseSessionWithCiNotified,
    ecommNotifyErrorData: EcommNtfErrorData? = null,
    ecommNotifyRetriedData: EcommNtfRetriedData? = null,
    updatedAt: Instant
) :
    Session,
    BaseSessionWithEcommNtfError(
        sessionWithCiNotified, ecommNotifyErrorData, ecommNotifyRetriedData, updatedAt) {

    override val status: SessionStatus = SessionStatus.ECOMM_NTF_ERROR

    constructor(
        session: BaseSessionWithCiNotified,
        event: EcommNtfErrorEvent
    ) : this(
        sessionWithCiNotified = session,
        ecommNotifyErrorData = event.data,
        updatedAt = event.timestamp)

    constructor(
        session: BaseSessionWithEcommNtfError,
        event: EcommNtfRetriedEvent
    ) : this(
        sessionWithCiNotified = session,
        ecommNotifyRetriedData = event.data,
        updatedAt = event.timestamp)

    override fun applyEvent(event: SessionEvent<*>): Session =
        when (event) {
            is EcommNotifyEvent -> EcommerceNotified(this, event)
            is EcommNtfRetriedEvent -> EcommerceNotifyError(this, event)
            is EcommNtfExpiredEvent -> EcommerceNotifyExpired(this, event)
            else -> this
        }
}
