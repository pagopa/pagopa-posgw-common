package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject
import java.util.UUID

@ValueObject @JvmInline value class SessionId(val id: UUID = UUID.randomUUID())
