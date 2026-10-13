package it.pagopa.posgw.common.documents.v1.sessions.data

import it.pagopa.posgw.common.domain.v1.Steps
import java.time.Instant

data class ExpiredData(val step: Steps, val timestamp: Instant)
