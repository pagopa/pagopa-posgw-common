package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.time.Instant

/**
 * Base data for an expired eCommerce notification.
 *
 * The original notification error and retry data are carried forward.
 *
 * @param sessionWithEcommNtfError failed notification state being expired
 * @param ecommNtfExpiredRetriedData retry data recorded after expiry, if any
 * @param updatedAt time the expired state was created
 */
abstract class BaseSessionWithEcommNtfExpired(
    sessionWithEcommNtfError: BaseSessionWithEcommNtfError,
    val ecommNtfExpiredRetriedData: EcommNtfRetriedData?,
    updatedAt: Instant
) :
    BaseSessionWithEcommNtfError(
        sessionWithCiNotified = sessionWithEcommNtfError,
        ecommNotifyErrorData = sessionWithEcommNtfError.ecommNotifyErrorData,
        ecommNotifyRetriedData = sessionWithEcommNtfError.ecommNotifyRetriedData,
        updatedAt = updatedAt)
