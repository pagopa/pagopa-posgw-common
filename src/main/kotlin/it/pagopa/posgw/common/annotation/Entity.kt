package it.pagopa.posgw.common.annotation

/**
 * <p>
 * An annotation for entities, as in the Domain Driven Design (DDD) sense. Entities are composites
 * made of value objects. Their primary feature is that they have an identity and can mutate over
 * time. </p>
 *
 * <p>
 * Entities can be distinguished from one another by their identifier. </p>
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class Entity
