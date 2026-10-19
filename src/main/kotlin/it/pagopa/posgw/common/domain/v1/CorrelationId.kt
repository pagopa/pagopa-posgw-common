package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject
import java.util.UUID

@ValueObject @JvmInline value class CorrelationId(val value: UUID = UUID.randomUUID()) {
    companion object {
        fun of(uuid: String) = CorrelationId(UUID.fromString(uuid))
    }
}
