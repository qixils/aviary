package dev.qixils.aviary.common.messaging

/**
 * An object that can be encoded as bytes for sending as a plugin message
 * to or from the proxy server.
 */
// TODO: use unit tests and reflection to ensure subclasses have a byte[] constructor
interface Message {

	/**
	 * Encodes this message as bytes.
	 *
	 * @return encoded message
	 */
	fun encode(): ByteArray

	/**
	 * The type of this message.
	 */
	val type: MessageType<*>
		get() = MessageType.byClass(javaClass)
}
