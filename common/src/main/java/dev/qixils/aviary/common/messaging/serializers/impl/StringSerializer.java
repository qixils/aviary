package dev.qixils.aviary.common.messaging.serializers.impl;

import dev.qixils.aviary.common.messaging.serializers.ByteSerializer;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

/**
 * A {@link ByteSerializer} for {@link String}s.
 */
public class StringSerializer implements ByteSerializer<String> {

	@Override
	public byte @NotNull [] serialize(@NotNull String string) {
		return string.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public @NotNull String deserialize(byte @NotNull [] data) {
		return new String(data, StandardCharsets.UTF_8);
	}
}
