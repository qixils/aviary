package dev.qixils.aviary.common.messaging.serializers

/**
 * An extension of [ByteSerializer] which also supports deserializing from a single byte.
 */
interface SingletonByteSerializer<T : Any> : ByteSerializer<T> {

	/**
	 * Converts a single byte to an object.
	 *
	 * @param data the byte
	 * @return the object
	 */
	fun deserialize(data: Byte): T
}
