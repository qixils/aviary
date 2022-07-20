package dev.qixils.aviary.common.messaging.serializers.impl;

import dev.qixils.aviary.common.messaging.serializers.ByteSerializer;
import dev.qixils.aviary.common.messaging.serializers.ByteSerializers;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link ByteSerializer} for {@link Key}s.
 */
public class KeySerializer implements ByteSerializer<Key> {

	@Override
	public byte @NotNull [] serialize(@NotNull Key key) {
		return ByteSerializers.STRING.serialize(key.toString());
	}

	@Override
	public @NotNull Key deserialize(byte @NotNull [] data) {
		return Key.key(ByteSerializers.STRING.deserialize(data));
	}
}
