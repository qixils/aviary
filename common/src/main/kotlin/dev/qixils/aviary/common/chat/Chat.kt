package dev.qixils.aviary.common.chat

import dev.qixils.aviary.common.Aviary
import dev.qixils.aviary.common.messaging.MessageType
import dev.qixils.aviary.common.messaging.PluginChannel
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage
import net.kyori.adventure.key.Key
import java.util.*
import java.util.concurrent.CompletableFuture

// TODO: abstract? open? final?

/**
 * The manager of chat channels.
 */
class Chat(

	/**
	 * The [Aviary] instance that created this chat instance.
	 */
	protected val aviary: Aviary
) {

	/**
	 * The local map of tracked channels.
	 */
	protected val registeredChannels: Map<Key, ChatChannel> = HashMap()

	/**
	 * Gets the [PluginChannel] used for determining if a chat channel is registered
	 * by any server responsible for the given player.
	 *
	 * @param player the UUID of the player
	 */
	protected fun pluginChannel(player: UUID): PluginChannel {
		val pluginChannel = aviary.pluginChannel("aviary.chat", player)
		if (!pluginChannel.hasAskHandler(MessageType.CHAT_ASK)) pluginChannel.registerAskHandler(
			MessageType.CHAT_ASK
		) { ask: ChatAskMessage -> askHandler(ask) }
		return pluginChannel
	}

	/**
	 * The handler applied to the [PluginChannel] to handle chat ask messages.
	 *
	 * @param ask an incoming ask message
	 * @return the reply message
	 */
	protected fun askHandler(ask: ChatAskMessage): BooleanReplyMessage {
		return BooleanReplyMessage(registeredChannels.containsKey(ask.value))
	}

	/**
	 * Determines if the specified chat channel is known to any server responsible for the given
	 * player.
	 *
	 * @param id     the id of the channel to check
	 * @param player the UUID of the player to check
	 * @return true if the channel is known, false otherwise
	 */
	fun isKnownChannel(id: Key, player: UUID): CompletableFuture<Boolean> {
		if (registeredChannels.containsKey(id)) return CompletableFuture.completedFuture(true)
		val channel = pluginChannel(player)
		return channel.ask(ChatAskMessage(id)).thenApply(BooleanReplyMessage::value)
	}
}
