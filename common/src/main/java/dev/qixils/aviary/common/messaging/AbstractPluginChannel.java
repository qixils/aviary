package dev.qixils.aviary.common.messaging;

/**
 * A utility class which implements some basic functionality of a plugin channel.
 */
public abstract class AbstractPluginChannel implements PluginChannel {
	private byte id = 0x00;

	@Override
	public byte nextId() {
		return id++;
	}
}
