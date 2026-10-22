package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.AuthRequestRetriedData
import java.time.Instant

/**
 * Base data for an authorization-request error state, retaining its transaction context.
 *
 * @param sessionWithTransactionAct transaction state preceding the authorization request
 * @param authRequestErrorData details of the request error
 * @param authRequestRetriedData latest retry data, if a retry has occurred
 * @param updatedAt time of the latest event
 */
abstract class BaseSessionWithAuthError(
    sessionWithTransactionAct: BaseSessionWithTransactionAct,
    val authRequestErrorData: AuthRequestErrorData,
    val authRequestRetriedData: List<AuthRequestRetriedData>,
    updatedAt: Instant
) :
    BaseSessionWithTransactionAct(
        initializedSession = sessionWithTransactionAct,
        transactionActivateData = sessionWithTransactionAct.transactionActivateData,
        updatedAt = updatedAt)
