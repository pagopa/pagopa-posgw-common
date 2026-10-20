package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfErrorData
import it.pagopa.posgw.common.documents.v1.sessions.data.EcommNtfRetriedData
import java.time.Instant

/**
 * Base data for a failed eCommerce notification.
 *
 * @param sessionWithCiNotified creditor-institution-notified state retained by this state
 * @param ecommNotifyErrorData details of the notification failure
 * @param ecommNotifyRetriedData latest retry data, if a retry has occurred
 * @param updatedAt time of the latest event
 */
abstract class BaseSessionWithEcommNtfError(
    sessionWithCiNotified: BaseSessionWithCiNotified,
    val ecommNotifyErrorData: EcommNtfErrorData,
    val ecommNotifyRetriedData: EcommNtfRetriedData?,
    updatedAt: Instant
) : BaseSessionWithCiNotified(sessionWithAuthComp = sessionWithCiNotified, updatedAt = updatedAt)
