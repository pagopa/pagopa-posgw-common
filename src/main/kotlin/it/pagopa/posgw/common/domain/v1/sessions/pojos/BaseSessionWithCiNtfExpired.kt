package it.pagopa.posgw.common.domain.v1.sessions.pojos

import it.pagopa.posgw.common.documents.v1.sessions.data.CiNtfRetriedData

/**
 * Base data for an expired creditor-institution notification.
 *
 * The original notification error and its retry data are carried forward.
 *
 * @param sessionWithCiNtfError failed notification state being expired
 * @param ciExpRetriedData retry data recorded after expiry, if any
 * @param updatedAt time the expired state was created
 */
abstract class BaseSessionWithCiNtfExpired(
    sessionWithCiNtfError: BaseSessionWithCiNtfError,
    val ciExpRetriedData: List<CiNtfRetriedData>,
    updatedAt: java.time.Instant
) :
    BaseSessionWithCiNtfError(
        sessionWithAuthComp = sessionWithCiNtfError,
        sessionWithCiNtfError.ciNtfErrorData,
        sessionWithCiNtfError.ciNtfRetriedData,
        updatedAt = updatedAt)
