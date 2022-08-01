package dev.qixils.aviary.common.messaging.impl

import dev.qixils.aviary.common.chat.AbstractSingletonMessage
import dev.qixils.aviary.common.messaging.Message
import dev.qixils.aviary.common.messaging.serializers.ByteSerializers

/**
 * A reply message containing a boolean value.
 */
class BooleanReplyMessage : AbstractSingletonMessage<Boolean>, Message {

	/**
	 * Constructs a new reply message with the provided status.
	 *
	 * @param status the status of the reply
	 */
	constructor(status: Boolean) : super(ByteSerializers.BOOLEAN, status)

	/**
	 * Constructs a new message from the provided byte array.
	 *
	 * @param data the byte array to deserialize
	 */
	constructor(data: ByteArray) : super(ByteSerializers.BOOLEAN, data)
}
