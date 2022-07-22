package dev.qixils.aviary.common;

import dev.qixils.aviary.common.chat.Chat;
import dev.qixils.aviary.common.messaging.PluginChannel;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The core of the Aviary API.
 */
public interface Aviary {

	/**
	 * The namespace used in Aviary's {@link net.kyori.adventure.key.Key keys}.
	 */
	@NotNull String NAMESPACE = "aviary";

	/**
	 * Gets the {@link PluginChannel} for the given name and for the given player's server.
	 *
	 * @param name   the name of the channel
	 * @param player the UUID of the player
	 * @return the channel
	 */
	@NotNull PluginChannel getPluginChannel(@NotNull String name, @NotNull UUID player);

	/**
	 * Gets the {@link Chat} instance for managing chat channels.
	 *
	 * @return the chat instance
	 */
	@NotNull Chat getChat();

	// TODO: registry stuff? (assuming i ever use the damn thing)
}
