package dev.qixils.aviary.common

import dev.qixils.aviary.common.chat.Chat
import dev.qixils.aviary.common.messaging.PluginChannel
import java.util.*

/**
 * The core of the Aviary API.
 */
interface Aviary {

	/**
	 * Gets the [PluginChannel] for the given name and for the given player's server.
	 *
	 * @param name   the name of the channel
	 * @param player the UUID of the player
	 * @return the channel
	 */
	fun pluginChannel(name: String, player: UUID): PluginChannel

	/**
	 * The [Chat] instance for managing chat channels.
	 */
	val chat: Chat

	// TODO: registry stuff? (assuming I ever use the damn thing)

	companion object {

		/**
		 * The namespace used in Aviary's [keys][net.kyori.adventure.key.Key].
		 */
		const val NAMESPACE = "aviary"
	}
}
