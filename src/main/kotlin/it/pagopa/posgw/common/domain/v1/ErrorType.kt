package it.pagopa.posgw.common.domain.v1

/**
 * Enum representing different types of errors that can occur.
 *
 * @property value The string representation of the error type.
 */
enum class ErrorType(val value: String) {

    COMMUNICATION_ERROR("COMMUNICATION_ERROR"),
    KO_RESPONSE_RECEIVED("KO_RESPONSE_RECEIVED")
}
