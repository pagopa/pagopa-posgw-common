package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.documents.BaseSessionEvent
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.DebtPosition
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.ErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivatedData
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * MongoDB read model containing a session's current status and accumulated data.
 *
 * @param id session identifier
 * @param correlationId correlation identifier
 * @param status current session status
 * @param createdAt session creation time
 * @param updatedAt time of the latest state change
 * @param pspList payment service providers associated with the session
 * @param debtPositions debt positions created for the session
 * @param transaction transaction activation data, if available
 * @param authorizationRequest authorization request data, if available
 * @param notification notification timestamps, if available
 * @param error session-level error details, if available
 * @param authRequestError authorization request error details, if available
 * @param ciNtfError creditor-institution notification error details, if available
 * @param ecommNtfErrorData eCommerce notification error details, if available
 * @param events events recorded for the session
 */
@Document("sessions")
open class SessionView(
    @Id open val id: String,
    open val correlationId: String,
    val status: String,
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
    /**
     * Provider identifiers displayed in the session view.
     *
     * @param id provider identifier
     * @param brokerId broker identifier
     * @param channelId channel identifier
     */
    data class Psp(val id: String, val brokerId: String, val channelId: String)

    /**
     * Times when the creditor institution and eCommerce notifications completed.
     *
     * @param eCommerceNotifiedAt eCommerce notification time
     * @param ciNotifiedAt creditor-institution notification time
     */
    data class Notification(val eCommerceNotifiedAt: Instant, val ciNotifiedAt: Instant)
}
