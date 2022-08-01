package dev.qixils.aviary.common.messaging

/**
 * A utility class which implements some basic functionality of a plugin channel.
 */
abstract class AbstractPluginChannel : PluginChannel {

	private var msgId: Byte = 0x00

	override fun nextId(): Byte {
		return msgId++
	}
}
