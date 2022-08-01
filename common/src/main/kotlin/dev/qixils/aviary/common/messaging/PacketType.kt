package dev.qixils.aviary.common.messaging

/**
 * Determines the general purpose of a message and whether a reply is expected.
 */
enum class PacketType(
	/**
	 * Determines whether a reply is expected.
	 */
	val isReplyExpected: Boolean
) {

	/**
	 * A message that does not expect a reply.
	 */
	VOID(false),

	/**
	 * A message that expects a reply.
	 */
	ASK(true),

	/**
	 * A message that is sent as a reply to an ask.
	 */
	REPLY(false);
}
