package dev.qixils.aviary.common.messaging.serializers.impl

import dev.qixils.aviary.common.messaging.serializers.ByteSerializer
import dev.qixils.aviary.common.messaging.serializers.ByteSerializers
import java.util.*

/**
 * A [ByteSerializer] for [Optional]s.
 */
class OptionalSerializer<T : Any>(
	/**
	 * The [ByteSerializer] for the [Optional]'s value.
	 */
	private val valueSerializer: ByteSerializer<T>
) : ByteSerializer<Optional<T>> {

	override fun serialize(obj: Optional<T>): ByteArray {
		if (obj.isEmpty)
			return ByteSerializers.BOOLEAN.serialize(false)
		val truthyByte = ByteSerializers.BOOLEAN.serialize(true)[0]
		val objBytes = valueSerializer.serialize(obj.get())
		return ByteArray(objBytes.size + 1).apply {
			set(0, truthyByte)
			System.arraycopy(objBytes, 0, this, 1, objBytes.size)
		}
	}

	override fun deserialize(data: ByteArray): Optional<T> {
		if (!ByteSerializers.BOOLEAN.deserialize(data[0]))
			return Optional.empty()
		return Optional.of(valueSerializer.deserialize(data.sliceArray(1 until data.size)))
	}
}
