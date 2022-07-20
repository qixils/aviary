package dev.qixils.aviary.common.messaging.serializers;

import org.jetbrains.annotations.NotNull;

/**
 * An object which converts objects to and from byte arrays.
 *
 * @param <T> the type of object to serialize
 */
public interface ByteSerializer<T> {

	/**
	 * Converts an object to a byte array.
	 *
	 * @param t the object to convert
	 * @return the byte array
	 */
	byte@NotNull[] serialize(@NotNull T t);

	/**
	 * Converts a byte array to an object.
	 *
	 * @param data the byte array
	 * @return the object
	 */
	@NotNull
	T deserialize(byte@NotNull[] data);
}
