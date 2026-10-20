package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Debt positions created for a session.
 *
 * @param depPositions debt positions returned by the creation operation
 */
data class DebtPositionCreatedData(val depPositions: List<DebtPosition>)
