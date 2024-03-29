package dev.qixils.aviary.common.chat

import dev.qixils.aviary.common.messaging.AskMessage
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage
import dev.qixils.aviary.common.messaging.serializers.ByteSerializers
import net.kyori.adventure.key.Key

/**
 * A message which asks the connected server if it is aware of a specific chat channel.
 */
class ChatAskMessage : AbstractSingletonMessage<Key>, AskMessage<BooleanReplyMessage> {

	/**
	 * Constructs a new message which will ask about the existence of the specified chat channel.
	 *
	 * @param channelId the ID of the channel to ask about
	 */
	constructor(channelId: Key) : super(ByteSerializers.KEY, channelId)

	/**
	 * Constructs a new message from the provided byte array.
	 *
	 * @param data the byte array to deserialize
	 */
	constructor(data: ByteArray) : super(ByteSerializers.KEY, data)
}
