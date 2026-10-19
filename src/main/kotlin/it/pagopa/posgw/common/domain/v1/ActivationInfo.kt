package it.pagopa.posgw.common.domain.v1

import it.pagopa.posgw.common.annotation.ValueObject

/**
 * Describes the payment and outcome information associated with activating a transaction.
 *
 * @property paymentToken Token associated with the payment instrument.
 * @property rptId Identifier of the payment notice being activated.
 * @property amount Payment amount.
 * @property outcome Result of the activation.
 * @property reason Optional explanation when the activation is unsuccessful.
 */
@ValueObject
data class ActivationInfo(
    val paymentToken: PaymentToken,
    val rptId: RptId,
    val amount: Long,
    val outcome: Outcome,
    val reason: String? = null
) {

    /** Result of an activation attempt. */
    enum class Outcome {
        OK,
        KO
    }
}
