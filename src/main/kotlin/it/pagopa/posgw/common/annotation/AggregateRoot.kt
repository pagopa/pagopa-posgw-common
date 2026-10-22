package it.pagopa.posgw.common.annotation

/**
 * An annotation for aggregate roots, as in the Domain Driven Design (DDD) sense. Aggregate roots
 * are composites of entities and value objects that are responsible for maintaining business logic
 * invariants. </p>
 *
 * <p>
 * Each aggregate root has an identifier (an {@link
 * it.pagopa.posgw.common.annotation.AggregateRootId}) which can be used to refer to this aggregate
 * root by other aggregate roots. Entities inside aggregate roots should never be referenced by
 * external aggregate roots. </p>
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class AggregateRoot()
