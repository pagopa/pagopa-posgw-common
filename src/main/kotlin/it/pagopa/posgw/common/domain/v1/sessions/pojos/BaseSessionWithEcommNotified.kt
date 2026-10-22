package it.pagopa.posgw.common.domain.v1.sessions.pojos

import java.time.Instant

/**
 * Base data for an eCommerce-notified state.
 *
 * The creditor-institution state immediately preceding this notification is retained.
 *
 * @param sessionWithCiNotified creditor-institution-notified predecessor
 * @param updatedAt time the eCommerce notification state was created
 */
abstract class BaseSessionWithEcommNotified(
    sessionWithCiNotified: BaseSessionWithCiNotified,
    updatedAt: Instant
) : BaseSessionWithCiNotified(sessionWithAuthComp = sessionWithCiNotified, updatedAt = updatedAt) {
    /** Creditor-institution-notified state immediately preceding this notification. */
    val sessionBeforeEcommNtf: BaseSessionWithCiNotified = sessionWithCiNotified
}
