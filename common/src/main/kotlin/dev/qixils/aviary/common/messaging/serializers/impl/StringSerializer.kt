package dev.qixils.aviary.common.messaging.serializers.impl

import dev.qixils.aviary.common.messaging.serializers.ByteSerializer
import java.nio.charset.StandardCharsets

/**
 * A [ByteSerializer] for [String]s.
 */
class StringSerializer : ByteSerializer<String> {

	override fun serialize(obj: String): ByteArray {
		return obj.toByteArray(StandardCharsets.UTF_8)
	}

	override fun deserialize(data: ByteArray): String {
		return String(data, StandardCharsets.UTF_8)
	}
}
