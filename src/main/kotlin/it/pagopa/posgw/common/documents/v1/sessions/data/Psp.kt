package it.pagopa.posgw.common.documents.v1.sessions.data

/**
 * Payment service provider configured for a broker and payment channel.
 *
 * @property id Provider identifier.
 * @property brokerId Identifier of the provider's broker.
 * @property channelId Identifier of the provider's payment channel.
 */
data class Psp(var id: String, var brokerId: String, var channelId: String)
