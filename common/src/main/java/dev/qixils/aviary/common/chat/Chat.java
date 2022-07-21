package dev.qixils.aviary.common.chat;

import dev.qixils.aviary.common.Aviary;
import dev.qixils.aviary.common.messaging.MessageType;
import dev.qixils.aviary.common.messaging.PluginChannel;
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
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
	private @Nullable PluginChannel pluginChannel;

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
	 * by any server.
	 */
	@NotNull
	protected PluginChannel getPluginChannel() {
		if (pluginChannel != null) return pluginChannel;

		// get plugin channel and register ask handler
		pluginChannel = aviary.getPluginChannel("aviary.chat");
		pluginChannel.registerAskHandler(MessageType.CHAT_ASK, ask -> new BooleanReplyMessage(registeredChannels.containsKey(ask.getValue())));
		return pluginChannel;
	}

	/**
	 * Determines if the specified chat channel is known to this server or the connected server.
	 *
	 * @param id the id of the channel to check
	 * @return true if the channel is known, false otherwise
	 */
	public CompletableFuture<Boolean> isKnownChannel(@NotNull Key id) {
		if (registeredChannels.containsKey(id))
			return CompletableFuture.completedFuture(true);
		PluginChannel channel = this.getPluginChannel();
		return channel.ask(new ChatAskMessage(id)).thenApply(BooleanReplyMessage::getValue);
	}
}
