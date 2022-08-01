package dev.qixils.aviary.common.messaging.serializers

import dev.qixils.aviary.common.messaging.serializers.impl.BooleanSerializer
import dev.qixils.aviary.common.messaging.serializers.impl.KeySerializer
import dev.qixils.aviary.common.messaging.serializers.impl.StringSerializer
import net.kyori.adventure.key.Key

/**
 * A utility class holding instances of general-purpose [serializers][ByteSerializer].
 */
object ByteSerializers {

	/**
	 * A [SingletonByteSerializer] for [Boolean]s.
	 */
	val BOOLEAN: SingletonByteSerializer<Boolean> = BooleanSerializer()

	/**
	 * A [ByteSerializer] for [String]s.
	 */
	val STRING: ByteSerializer<String> = StringSerializer()

	/**
	 * A [ByteSerializer] for [Key]s.
	 */
	val KEY: ByteSerializer<Key> = KeySerializer()
}
