package dev.qixils.aviary.common.messaging.serializers.impl;

import dev.qixils.aviary.common.messaging.serializers.SingletonByteSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link SingletonByteSerializer} for {@link Boolean}s.
 */
public class BooleanSerializer implements SingletonByteSerializer<Boolean> {
	private static final byte TRUE = (byte) 0x01;
	private static final byte FALSE = (byte) 0x00;


	@Override
	public byte @NotNull [] serialize(@NotNull Boolean aBoolean) {
		return new byte[] { aBoolean ? TRUE : FALSE };
	}

	@Override
	public @NotNull Boolean deserialize(byte @NotNull [] data) {
		if (data.length != 1)
			throw new IllegalArgumentException("data must be of length 1");
		return deserialize(data[0]);
	}

	@Override
	public @NotNull Boolean deserialize(byte data) {
		if (data == TRUE)
			return true;
		else if (data == FALSE)
			return false;
		else
			throw new IllegalArgumentException("data must be either 0x00 or 0x01, not " + data);
	}
}
