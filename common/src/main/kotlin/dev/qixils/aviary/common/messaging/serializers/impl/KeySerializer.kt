package dev.qixils.aviary.common.messaging.serializers.impl

import dev.qixils.aviary.common.messaging.serializers.ByteSerializer
import dev.qixils.aviary.common.messaging.serializers.ByteSerializers
import net.kyori.adventure.key.Key

/**
 * A [ByteSerializer] for [Key]s.
 */
class KeySerializer : ByteSerializer<Key> {

	override fun serialize(obj: Key): ByteArray {
		return ByteSerializers.STRING.serialize(obj.toString())
	}

	override fun deserialize(data: ByteArray): Key {
		return Key.key(ByteSerializers.STRING.deserialize(data))
	}
}
