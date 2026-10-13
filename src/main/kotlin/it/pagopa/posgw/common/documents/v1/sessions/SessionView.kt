package it.pagopa.posgw.common.documents.v1.sessions

import it.pagopa.posgw.common.documents.v1.sessions.data.TransactionActivetedData
import it.pagopa.posgw.common.domain.v1.DebtPosition
import it.pagopa.posgw.common.domain.v1.Psp
import it.pagopa.posgw.common.domain.v1.sessions.SessionStatus
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("sessions")
open class SessionView(
    @Id open val id: String,
    open val correlationId: String,
    val status: SessionStatus,
    @Indexed val createdAt: Instant,
    @Indexed val updatedAt: Instant,
    val pspList: List<Psp>,
    val debtPositions: List<DebtPosition>,
    val transaction: TransactionActivetedData
)
