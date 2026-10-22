package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

/**
 * Base data for a creditor-institution-notified state.
 *
 * The authorization outcome and the state that produced this notification are retained.
 *
 * @param sessionWithAuthComp authorization-completed state preceding notification
 * @param updatedAt time the notification state was created
 */
abstract class BaseSessionWithCiNotified(
    sessionWithAuthComp: BaseSessionWithAuthComp,
    updatedAt: Instant
) :
    BaseSessionWithAuthComp(
        sessionWithAuthReq = sessionWithAuthComp,
        authorizationData = sessionWithAuthComp.authorizationData,
        updatedAt = updatedAt) {
    /** Authorization-completed state immediately preceding the notification. */
    val sessionBeforeCiNtf: BaseSessionWithAuthComp = sessionWithAuthComp
}
