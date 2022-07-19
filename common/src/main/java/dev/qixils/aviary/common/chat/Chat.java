package dev.qixils.aviary.common.chat;

import dev.qixils.aviary.common.Aviary;
import dev.qixils.aviary.common.messaging.PluginChannel;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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
	 * by any server.
	 */
	@NotNull
	protected PluginChannel getPluginChannel() {
		return this.aviary.getPluginChannel("aviary.chat");
	}

	/**
	 * Determines if the specified chat channel is known to this server or the connected server.
	 *
	 * @param id the id of the channel to check
	 * @return true if the channel is known, false otherwise
	 */
	public boolean isKnownChannel(@NotNull Key id) {
		if (this.registeredChannels.containsKey(id))
			return true;
		// TODO: use plugin channel to ask connected server if it knows of the requested channel
	}
}
