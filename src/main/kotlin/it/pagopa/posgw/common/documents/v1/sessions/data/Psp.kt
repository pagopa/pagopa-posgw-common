package it.pagopa.posgw.common.documents.v1.sessions.data

import org.springframework.data.mongodb.core.mapping.Field

/**
 * Payment service provider configured for a broker and payment channel.
 *
 * @property id Provider identifier, mapped explicitly so MongoDB stores it as `id` and not `_id`.
 * @property brokerId Identifier of the provider's broker.
 * @property channelId Identifier of the provider's payment channel.
 */
data class Psp(@field:Field("id") var id: String, var brokerId: String, var channelId: String)
