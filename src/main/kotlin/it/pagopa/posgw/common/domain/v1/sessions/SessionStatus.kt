package it.pagopa.posgw.common.domain.v1.sessions

enum class SessionStatus(val status: String) {
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    ERROR("ERROR"),
    EXPIRED("EXPIRED")
}
