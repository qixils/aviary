package dev.qixils.aviary.common.messaging;

import dev.qixils.aviary.db.Identifiable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A channel which facilitates the communication of messages between two servers.
 */
public interface PluginChannel extends Identifiable<String> {

	/**
	 * Converts a message and its ID to a byte array.
	 *
	 * @param message the message to convert
	 * @param id      the ID of the message
	 * @return the byte array
	 */
	static byte[] asBytes(@NotNull Message message, byte id) {
		byte[] bytes = message.encode();
		byte[] fullMessage = new byte[bytes.length + 2];
		fullMessage[0] = id;
		fullMessage[1] = message.getType().getId();
		System.arraycopy(bytes, 0, fullMessage, 2, bytes.length);
		return fullMessage;
	}

	/**
	 * Generates an ID to use for a new non-reply message.
	 *
	 * @return a new ID
	 */
	byte nextId();

	/**
	 * Sends a raw array of bytes to the connected server.
	 *
	 * @param data the byte array to send
	 */
	void send(byte @NotNull [] data);

	/**
	 * Sends a message to the connected server.
	 *
	 * @param message the message to send
	 * @param id      the ID of the message
	 */
	default void send(@NotNull Message message, byte id) {
		if (message.getType().getPacketType().isReplyExpected())
			throw new IllegalArgumentException("Asks should be sent through #ask, not #send");
		send(asBytes(message, id));
	}

	/**
	 * Sends a message to the connected server.
	 *
	 * @param message the message to send
	 */
	default void send(@NotNull Message message) {
		send(message, nextId());
	}

	/**
	 * Sends a raw array of bytes to the connected server and returns a future that will be
	 * completed upon receiving a response.
	 *
	 * @param data the byte array to send
	 * @return a future that will be completed upon receiving a response or throw a
	 * {@link java.util.concurrent.TimeoutException TimeoutException} if a response is not received
	 */
	@NotNull
	CompletableFuture<Message> ask(byte @NotNull [] data);

	/**
	 * Sends a message to the connected server and returns a future that will be completed upon
	 * receiving a response.
	 *
	 * @param message the message to send
	 * @return a future that will be completed upon receiving a response or throw a
	 * {@link java.util.concurrent.TimeoutException TimeoutException} if a response is not received
	 */
	@NotNull
	default <R extends Message> CompletableFuture<R> ask(@NotNull AskMessage<R> message) {
		//noinspection unchecked
		return ask(asBytes(message, nextId())).thenApply(msg -> (R) msg);
	}

	/**
	 * Registers a handler for incoming {@link PacketType#VOID void messages} of the given type.
	 *
	 * @param type    the type of {@link PacketType#VOID void message} to handle
	 * @param handler the handler to register
	 * @throws IllegalArgumentException if the type is not {@link PacketType#VOID void} or if a
	 * 								    handler is already registered for the given type
	 */
	<M extends Message> void registerVoidHandler(@NotNull MessageType<M> type, @NotNull Consumer<M> handler);

	/**
	 * Registers a handler for incoming {@link PacketType#ASK ask messages} of the given type.
	 *
	 * @param type    the type of {@link PacketType#ASK ask message} to handle
	 * @param handler the handler to register
	 * @throws IllegalArgumentException if the type is not {@link PacketType#ASK ask} or if a
	 * 								    handler is already registered for the given type
	 */
	<M extends AskMessage<R>, R extends Message> void registerAskHandler(@NotNull MessageType<M> type, @NotNull Function<M, R> handler);

	/**
	 * Determines if a void handler has been registered for the given type.
	 *
	 * @param type the type to check
	 * @return true if a void handler is registered for the given type, false otherwise
	 */
	boolean hasVoidHandler(@NotNull MessageType<?> type);

	/**
	 * Determines if an ask handler has been registered for the given type.
	 *
	 * @param type the type to check
	 * @return true if an ask handler is registered for the given type, false otherwise
	 */
	boolean hasAskHandler(@NotNull MessageType<?> type);

	/**
	 * Determines if any handler has been registered for the given type.
	 *
	 * @param type the type to check
	 * @return true if any handler is registered for the given type, false otherwise
	 */
	default boolean hasHandler(@NotNull MessageType<?> type) {
		return hasVoidHandler(type) || hasAskHandler(type);
	}
}
