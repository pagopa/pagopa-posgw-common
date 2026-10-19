package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.time.Instant

abstract class BaseSessionWithEcommNtfError(
    val sessionWithCiNotified: BaseSessionWithCiNotified,
    val ecommNotifyErrorData: EcommNtfErrorData?,
    val ecommNotifyRetriedData: EcommNtfRetriedData?,
    updatedAt: Instant
) :
    BaseSessionWithCiNotified(
        sessionWithAuthComp = sessionWithCiNotified.sessionWithAuthComp, updatedAt = updatedAt)
