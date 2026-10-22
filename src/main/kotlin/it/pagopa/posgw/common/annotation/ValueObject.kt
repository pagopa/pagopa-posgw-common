package it.pagopa.posgw.common.annotation

/**
 * An annotation for value objects, as in the Domain Driven Design (DDD) sense. Value objects should
 * be immutable classes where each instance derives its semantics from the values it contains. Their
 * characterizing feature is that value objects have no identity.
 *
 * Value objects can have some form of input validation.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class ValueObject
