package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData

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
