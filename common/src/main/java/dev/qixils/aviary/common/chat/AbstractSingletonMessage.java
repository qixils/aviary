package dev.qixils.aviary.common.chat;

import dev.qixils.aviary.common.messaging.Message;
import dev.qixils.aviary.common.messaging.serializers.ByteSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * A message which stores only one serializable variable.
 *
 * @param <T> the type of the variable
 */
public abstract class AbstractSingletonMessage<T> implements Message {
	private final @NotNull T value;
	private final byte@NotNull[] data;

	/**
	 * Constructs a new message with the specified value.
	 *
	 * @param serializer the serializer to use for serializing the value
	 * @param value      the value to store
	 */
	protected AbstractSingletonMessage(@NotNull ByteSerializer<T> serializer, @NotNull T value) {
		this.value = value;
		this.data = serializer.serialize(value);
	}

	/**
	 * Constructs a new message from the provided byte array.
	 *
	 * @param serializer the serializer to use for deserializing the data
	 * @param data       the byte array to deserialize
	 */
	protected AbstractSingletonMessage(@NotNull ByteSerializer<T> serializer, byte@NotNull[] data) {
		this.value = serializer.deserialize(data);
		this.data = data;
	}

	/**
	 * Returns the value stored in this message.
	 *
	 * @return the value stored in this message
	 */
	@NotNull
	public T getValue() {
		return value;
	}

	@Override
	public byte[] encode() {
		return data;
	}
}
