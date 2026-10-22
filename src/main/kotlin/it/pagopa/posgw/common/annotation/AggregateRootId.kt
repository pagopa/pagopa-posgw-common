package it.pagopa.posgw.common.annotation

/**
 * <p>
 * An identifier for aggregate roots. Note that this identifier should be globally unique. </p>
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class AggregateRootId()
