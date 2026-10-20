package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData

abstract class BaseSessionWithCiNtfExpired(
    sessionWithCiNtfError: BaseSessionWithCiNtfError,
    val ciExpRetriedData: CiNtfRetriedData?,
    updatedAt: java.time.Instant
) :
    BaseSessionWithCiNtfError(
        sessionWithAuthComp = sessionWithCiNtfError,
        sessionWithCiNtfError.ciNtfErrorData,
        sessionWithCiNtfError.ciNtfRetriedData,
        updatedAt = updatedAt)
