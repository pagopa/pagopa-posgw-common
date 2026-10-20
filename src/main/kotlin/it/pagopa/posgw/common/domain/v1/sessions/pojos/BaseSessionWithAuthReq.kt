package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestedData
import java.time.Instant

/**
 * Base data for an authorization-requested state.
 *
 * @param sessionWithTransactionAct transaction state from which authorization was requested
 * @param authorizationRequestData data sent with the authorization request
 * @param updatedAt time the state was created
 */
abstract class BaseSessionWithAuthReq(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val authorizationRequestData: AuthRequestedData,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt) {
    /** Transaction state immediately preceding the authorization request. */
    val sessionBeforeAuthReq: BaseSessionWithTransactionAct = sessionWithTransactionAct
}
