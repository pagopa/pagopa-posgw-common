package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.documents.BaseSessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPosition
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("sessions")
open class SessionView(
    @Id open val id: String,
    open val correlationId: String,
    val status: SessionStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
    val pspList: List<Psp> = emptyList(),
    val debtPositions: List<DebtPosition> = emptyList(),
    val transaction: TransactionActivatedData? = null,
    val authorizationRequest: AuthRequestedData? = null,
    val notification: Notification? = null,
    val error: ErrorData? = null,
    val authRequestError: AuthRequestErrorData? = null,
    val ciNtfError: CiNtfErrorData? = null,
    val ecommNtfErrorData: EcommNtfErrorData? = null,
    val events: List<BaseSessionEvent<*>> = emptyList(),
) {
    data class Psp(val id: String, val brokerId: String, val channelId: String)

    data class Notification(val eCommerceNotifiedAt: Instant, val ciNotifiedAt: Instant)
}
