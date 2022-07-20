package dev.qixils.aviary.common.messaging;

/**
 * Determines the general purpose of a message and whether a reply is expected.
 */
public enum PacketType {
	/**
	 * A message that does not expect a reply.
	 */
	VOID(false),
	/**
	 * A message that expects a reply.
	 */
	ASK(true),
	/**
	 * A message that is sent as a reply to an {@link AskMessage}.
	 */
	REPLY(false);

	private final boolean replyExpected;

	PacketType(boolean replyExpected) {
		this.replyExpected = replyExpected;
	}

	/**
	 * Determines whether a reply is expected.
	 *
	 * @return {@code true} if a reply is expected, {@code false} otherwise
	 */
	public boolean isReplyExpected() {
		return replyExpected;
	}
}
