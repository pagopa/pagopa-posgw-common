package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthOutcomeReceivedData
import java.time.Instant

/**
 * Base data for an authorization-completed state.
 *
 * Authorization request data from the predecessor and the received outcome remain available.
 *
 * @param sessionWithAuthReq state that submitted the authorization request
 * @param authorizationData received authorization outcome
 * @param updatedAt time of the authorization outcome event
 */
abstract class BaseSessionWithAuthComp(
    sessionWithAuthReq: BaseSessionWithAuthReq,
    val authorizationData: AuthOutcomeReceivedData,
    updatedAt: Instant
) :
    BaseSessionWithAuthReq(
        sessionWithTransactionAct = sessionWithAuthReq,
        authorizationRequestData = sessionWithAuthReq.authorizationRequestData,
        updatedAt = updatedAt)
