package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

@ValueObject @JvmInline value class AuthorizationCode(val value: String)
