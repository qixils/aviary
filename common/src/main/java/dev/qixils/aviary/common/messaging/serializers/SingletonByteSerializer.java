package dev.qixils.aviary.common.messaging.serializers;

import org.jetbrains.annotations.NotNull;

/**
 * An extension of {@link ByteSerializer} which also supports deserializing from a single byte.
 */
public interface SingletonByteSerializer<T> extends ByteSerializer<T> {

	/**
	 * Converts a single byte to an object.
	 *
	 * @param data the byte
	 * @return the object
	 */
	@NotNull
	T deserialize(byte data);
}
