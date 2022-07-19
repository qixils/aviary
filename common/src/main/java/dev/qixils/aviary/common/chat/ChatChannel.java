package dev.qixils.aviary.common.chat;

import dev.qixils.aviary.db.Identifiable;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A chat channel that can be used to communicate with players.
 */
public interface ChatChannel extends Identifiable<Key>, Audience {

	/**
	 * Gets the name of this channel.
	 *
	 * @return the name of this channel
	 */
	@NotNull
	String getName();

	/**
	 * Formats a chat message for this channel.
	 * The returned {@link Optional} may be empty if the viewer should not be able to see the message.
	 * <p>
	 * Implementations are recommended to cache resulting components where possible.
	 *
	 * @param text   the text to format
	 * @param author the author of the message
	 * @param viewer the viewer of the message
	 * @return an {@link Optional} containing formatted message if applicable, empty otherwise
	 */
	@NotNull
	Optional<Component> formatMessage(@NotNull String text, @NotNull Identified author, @NotNull Identified viewer);
}
