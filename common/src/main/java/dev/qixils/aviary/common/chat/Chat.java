package dev.qixils.aviary.common.chat;

import dev.qixils.aviary.common.Aviary;
import dev.qixils.aviary.common.messaging.MessageType;
import dev.qixils.aviary.common.messaging.PluginChannel;
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The manager of chat channels.
 */
public class Chat { // TODO: abstract? final?
	/**
	 * The {@link Aviary} instance that created this chat instance.
	 */
	protected final Aviary aviary;
	/**
	 * The local map of tracked channels.
	 */
	protected final @NotNull Map<Key, ChatChannel> registeredChannels = new HashMap<>();

	/**
	 * Creates a new chat instance.
	 *
	 * @param aviary the {@link Aviary} instance creating this chat instance
	 */
	public Chat(@NotNull Aviary aviary) {
		this.aviary = aviary;
	}

	/**
	 * Gets the {@link PluginChannel} used for determining if a chat channel is registered
	 * by any server responsible for the given player.
	 *
	 * @param player the UUID of the player
	 */
	@NotNull
	protected PluginChannel getPluginChannel(@NotNull UUID player) {
		PluginChannel pluginChannel = aviary.getPluginChannel("aviary.chat", player);
		if (!pluginChannel.hasAskHandler(MessageType.CHAT_ASK))
			pluginChannel.registerAskHandler(MessageType.CHAT_ASK, this::askHandler);
		return pluginChannel;
	}

	/**
	 * The handler applied to the {@link PluginChannel} to handle chat ask messages.
	 *
	 * @param ask an incoming ask message
	 * @return the reply message
	 */
	@NotNull
	protected BooleanReplyMessage askHandler(ChatAskMessage ask) {
		return new BooleanReplyMessage(registeredChannels.containsKey(ask.getValue()));
	}

	/**
	 * Determines if the specified chat channel is known to any server responsible for the given
	 * player.
	 *
	 * @param id     the id of the channel to check
	 * @param player the UUID of the player to check
	 * @return true if the channel is known, false otherwise
	 */
	public CompletableFuture<Boolean> isKnownChannel(@NotNull Key id, @NotNull UUID player) {
		if (registeredChannels.containsKey(id))
			return CompletableFuture.completedFuture(true);
		PluginChannel channel = this.getPluginChannel(player);
		return channel.ask(new ChatAskMessage(id)).thenApply(BooleanReplyMessage::getValue);
	}
}
