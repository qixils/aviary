package dev.qixils.aviary.common.messaging

import dev.qixils.aviary.common.chat.ChatAskMessage
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage

/**
 * A type of message that can be sent to or from the proxy server.
 */
class MessageType<M : Message> private constructor(

	/**
	 * The id of this message type.
	 */
	val id: Byte,

	/**
	 * The java class of this message type.
	 */
	val messageClass: Class<M>,

	/**
	 * The packet type of this message type.
	 * This determines how the message should be handled by the connected server.
	 */
	val packetType: PacketType
) {

	companion object {
		// registry maps
		private val BY_ID: MutableMap<Byte, MessageType<*>> = HashMap()
		private val BY_CLASS: MutableMap<Class<out Message>, MessageType<*>> = HashMap()

		// instances
		public val CHAT_ASK =
			register(0x00.toByte(), ChatAskMessage::class.java, PacketType.ASK)
		public val BOOLEAN_REPLY =
			register(0x01.toByte(), BooleanReplyMessage::class.java, PacketType.REPLY)

		// registry methods
		private fun <T : Message> register(
			id: Byte,
			clazz: Class<T>,
			packetType: PacketType
		): MessageType<T> {
			val type = MessageType(id, clazz, packetType)
			BY_ID[id] = type
			BY_CLASS[clazz] = type
			return type
		}

		/**
		 * Gets the message type with the given id.
		 *
		 * @param id the id of the message type
		 * @return the message type with the given id
		 * @throws IllegalArgumentException if no message type with the given id exists
		 */
		fun byId(id: Byte): MessageType<*> {
			require(BY_ID.containsKey(id)) { "No message type with id $id" }
			return BY_ID[id]!!
		}

		/**
		 * Gets the message type with the given class.
		 *
		 * @param clazz the class of the message type
		 * @return the message type with the given class
		 * @throws IllegalArgumentException if no message type with the given class exists
		 */
		fun byClass(clazz: Class<out Message>): MessageType<*> {
			require(BY_CLASS.containsKey(clazz)) { "No message type with class $clazz" }
			return BY_CLASS[clazz]!!
		}
	}
}
