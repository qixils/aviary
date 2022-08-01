package dev.qixils.aviary.common.chat

import dev.qixils.aviary.db.FixedNameable
import dev.qixils.aviary.db.Identifiable
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.identity.Identified
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component

/**
 * A chat channel that can be used to communicate with players.
 */
interface ChatChannel : FixedNameable, Identifiable<Key>, Audience {

	/**
	 * Formats a chat message for this channel.
	 * The returned component may be null if the viewer should not be able to see the message.
	 *
	 * @param text   the text to format
	 * @param author the author of the message
	 * @param viewer the viewer of the message
	 * @return a component if applicable, otherwise null
	 */
	fun formatMessage(text: String, author: Identified, viewer: Identified): Component?
}
