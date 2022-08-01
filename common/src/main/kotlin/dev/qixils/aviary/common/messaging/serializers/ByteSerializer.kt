package dev.qixils.aviary.common.messaging.serializers

/**
 * An object which converts objects to and from byte arrays.
 *
 * @param T the type of object to serialize
*/
interface ByteSerializer<T : Any> {

	/**
	 * Converts an object to a byte array.
	 *
	 * @param obj the object to convert
	 * @return the byte array
	 */
	fun serialize(obj: T): ByteArray

	/**
	 * Converts a byte array to an object.
	 *
	 * @param data the byte array
	 * @return the object
	 */
	fun deserialize(data: ByteArray): T
}
