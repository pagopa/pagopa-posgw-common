package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Session status at the time the session expired.
 *
 * @param atStatus status from which the session expired
 */
data class ExpiredData(val atStatus: String)
