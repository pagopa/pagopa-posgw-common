package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.time.Instant

abstract class BaseSessionWithEcommNtfExpired(
    val sessionWithEcommNtfError: BaseSessionWithEcommNtfError,
    val ecommNtfExpiredRetriedData: EcommNtfRetriedData?,
    updatedAt: Instant
) :
    BaseSessionWithEcommNtfError(
        sessionWithCiNotified = sessionWithEcommNtfError.sessionWithCiNotified,
        ecommNotifyErrorData = sessionWithEcommNtfError.ecommNotifyErrorData,
        ecommNotifyRetriedData = sessionWithEcommNtfError.ecommNotifyRetriedData,
        updatedAt = updatedAt)
