package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData

/**
 * Base data for a failed creditor-institution notification.
 *
 * @param sessionWithAuthComp authorization-completed state retained by this state
 * @param ciNtfErrorData details of the notification failure
 * @param ciNtfRetriedData latest retry data, if a retry has occurred
 * @param updatedAt time of the latest event
 */
abstract class BaseSessionWithCiNtfError(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    val ciNtfErrorData: CiNtfErrorData,
    val ciNtfRetriedData: CiNtfRetriedData?,
    updatedAt: java.time.Instant
) :
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthComp,
        authorizationData = sessionWithAuthComp.authorizationData,
        updatedAt = updatedAt)
