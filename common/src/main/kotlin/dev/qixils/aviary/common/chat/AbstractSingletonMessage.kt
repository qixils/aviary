package dev.qixils.aviary.common.chat

import dev.qixils.aviary.common.messaging.Message
import dev.qixils.aviary.common.messaging.serializers.ByteSerializer

/**
 * A message which stores only one serializable variable.
 *
 * @param T the type of the variable
*/
abstract class AbstractSingletonMessage<T : Any> : Message {

	/**
	 * The value stored in this message.
	 */
	val value: T

	/**
	 * The byte array representing the value stored in this message.
	 */
	private val data: ByteArray

	/**
	 * Constructs a new message with the specified value.
	 *
	 * @param serializer the serializer to use for serializing the value
	 * @param value      the value to store
	 */
	protected constructor(serializer: ByteSerializer<T>, value: T) {
		this.value = value
		data = serializer.serialize(value)
	}

	/**
	 * Constructs a new message from the provided byte array.
	 *
	 * @param serializer the serializer to use for deserializing the data
	 * @param data       the byte array to deserialize
	 */
	protected constructor(serializer: ByteSerializer<T>, data: ByteArray) {
		value = serializer.deserialize(data)
		this.data = data
	}

	override fun encode(): ByteArray {
		return data
	}
}
