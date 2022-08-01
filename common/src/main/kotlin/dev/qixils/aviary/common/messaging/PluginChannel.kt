package dev.qixils.aviary.common.messaging

import dev.qixils.aviary.db.Identifiable
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import java.util.function.Function

/**
 * A channel which facilitates the communication of messages between two servers.
 */
interface PluginChannel : Identifiable<String> {

	/**
	 * Generates an ID to use for a new non-reply message.
	 *
	 * @return a new ID
	 */
	fun nextId(): Byte

	/**
	 * Sends a raw array of bytes to the connected server.
	 *
	 * @param data the byte array to send
	 */
	fun send(data: ByteArray)

	/**
	 * Sends a message to the connected server.
	 *
	 * @param message the message to send
	 * @param id      optional: the ID of the message; defaults to [nextId]
	 */
	fun send(message: Message, id: Byte = nextId()) {
		require(!message.type.packetType.isReplyExpected) {
			"Asks should be sent through #ask, not #send"
		}
		send(asBytes(message, id))
	}

	/**
	 * Sends a raw array of bytes to the connected server and returns a future that will be
	 * completed upon receiving a response.
	 *
	 * @param data the byte array to send
	 * @return a future that will be completed upon receiving a response or throw a
	 * [TimeoutException][java.util.concurrent.TimeoutException] if a response is not received
	 */
	fun ask(data: ByteArray): CompletableFuture<Message>

	/**
	 * Sends a message to the connected server and returns a future that will be completed upon
	 * receiving a response.
	 *
	 * @param message the message to send
	 * @return a future that will be completed upon receiving a response or throw a
	 * [TimeoutException][java.util.concurrent.TimeoutException] if a response is not received
	 */
	fun <R : Message> ask(message: AskMessage<R>): CompletableFuture<R> {
		return ask(asBytes(message, nextId())).thenApply { msg: Message -> msg as R }
	}

	/**
	 * Registers a handler for incoming [void messages][PacketType.VOID] of the given type.
	 *
	 * @param type    the type of [void message][PacketType.VOID] to handle
	 * @param handler the handler to register
	 * @throws IllegalArgumentException if the type is not [void][PacketType.VOID] or if a
	 * handler is already registered for the given type
	 */
	fun <M : Message> registerVoidHandler(type: MessageType<M>, handler: Consumer<M>)

	/**
	 * Registers a handler for incoming [ask messages][PacketType.ASK] of the given type.
	 *
	 * @param type    the type of [ask message][PacketType.ASK] to handle
	 * @param handler the handler to register
	 * @throws IllegalArgumentException if the type is not [ask][PacketType.ASK] or if a
	 * handler is already registered for the given type
	 */
	fun <M : AskMessage<R>, R : Message> registerAskHandler(
		type: MessageType<M>,
		handler: Function<M, R>
	)

	/**
	 * Determines if a void handler has been registered for the given type.
	 *
	 * @param type the type to check
	 * @return true if a void handler is registered for the given type, false otherwise
	 */
	fun hasVoidHandler(type: MessageType<*>): Boolean

	/**
	 * Determines if an ask handler has been registered for the given type.
	 *
	 * @param type the type to check
	 * @return true if an ask handler is registered for the given type, false otherwise
	 */
	fun hasAskHandler(type: MessageType<*>): Boolean

	/**
	 * Determines if any handler has been registered for the given type.
	 *
	 * @param type the type to check
	 * @return true if any handler is registered for the given type, false otherwise
	 */
	fun hasHandler(type: MessageType<*>): Boolean {
		return hasVoidHandler(type) || hasAskHandler(type)
	}

	companion object {

		/**
		 * Converts a message and its ID to a byte array.
		 *
		 * @param message the message to convert
		 * @param id      the ID of the message
		 * @return the byte array
		 */
		fun asBytes(message: Message, id: Byte): ByteArray {
			val bytes = message.encode()
			return ByteArray(bytes.size + 2).apply {
				this[0] = id
				this[1] = message.type.id
				System.arraycopy(bytes, 0, this, 2, bytes.size)
			}
		}
	}
}
