package dev.qixils.aviary.common.messaging.serializers;

import dev.qixils.aviary.common.messaging.serializers.impl.BooleanSerializer;
import dev.qixils.aviary.common.messaging.serializers.impl.KeySerializer;
import dev.qixils.aviary.common.messaging.serializers.impl.StringSerializer;
import net.kyori.adventure.key.Key;

/**
 * A utility class holding instances of general-purpose {@link ByteSerializer serializers}.
 */
public final class ByteSerializers {
	private ByteSerializers() {
		throw new UnsupportedOperationException("This class is not meant to be instantiated");
	}

	/**
	 * A {@link SingletonByteSerializer} for {@link Boolean}s.
	 */
	public static final SingletonByteSerializer<Boolean> BOOLEAN = new BooleanSerializer();

	/**
	 * A {@link ByteSerializer} for {@link String}s.
	 */
	public static final ByteSerializer<String> STRING = new StringSerializer();

	/**
	 * A {@link ByteSerializer} for {@link Key}s.
	 */
	public static final ByteSerializer<Key> KEY = new KeySerializer();
}
