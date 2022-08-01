package dev.qixils.aviary.common.messaging.serializers.impl

import dev.qixils.aviary.common.messaging.serializers.SingletonByteSerializer

/**
 * A [SingletonByteSerializer] for [Boolean]s.
 */
class BooleanSerializer : SingletonByteSerializer<Boolean> {

	override fun serialize(obj: Boolean): ByteArray {
		return byteArrayOf(if (obj) TRUE else FALSE)
	}

	override fun deserialize(data: ByteArray): Boolean {
		require(data.size == 1) { "data must be of length 1" }
		return deserialize(data[0])
	}

	override fun deserialize(data: Byte): Boolean {
		return when (data) {
			TRUE -> true
			FALSE -> false
			else -> throw IllegalArgumentException("data must be either 0x00 or 0x01, not 0x%02x".format(data))
		}
	}

	companion object {
		private const val TRUE = 0x01.toByte()
		private const val FALSE = 0x00.toByte()
	}
}
