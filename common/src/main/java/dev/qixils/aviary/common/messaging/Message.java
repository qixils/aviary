package dev.qixils.aviary.common.messaging;

import org.jetbrains.annotations.NotNull;

/**
 * An object that can be encoded as bytes for sending as a plugin message
 * to or from the proxy server.
 */
// TODO: use unit tests and reflection to ensure subclasses have a byte[] constructor
public interface Message {

	/**
	 * Encodes this message as bytes.
	 *
	 * @return encoded message
	 */
	byte[] encode();

	/**
	 * Gets the type of this message.
	 *
	 * @return the type of this message
	 */
	@NotNull
	default MessageType<?> getType() {
		return MessageType.byClass(getClass());
	}
}
