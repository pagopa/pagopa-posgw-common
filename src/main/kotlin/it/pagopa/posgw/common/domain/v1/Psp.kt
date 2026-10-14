package it.pagopa.posgw.common.domain.v1

/**
 * Payment service provider configured for a broker and payment channel.
 *
 * @property id Provider identifier.
 * @property brokerId Identifier of the provider's broker.
 * @property channelId Identifier of the provider's payment channel.
 */
data class Psp(var id: String, var brokerId: BrokerId, var channelId: ChannelId)
