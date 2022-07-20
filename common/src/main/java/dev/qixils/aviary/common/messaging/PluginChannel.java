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
	 * Sends a raw array of bytes to the connected server.
	 *
	 * @param data the byte array to send
	 */
	void send(byte @NotNull [] data);

	/**
	 * Sends a message to the connected server.
	 *
	 * @param message the message to send
	 */
	default void send(@NotNull Message message) {
		if (message instanceof AskMessage ask) {
			ask(ask);
			return;
		}
		byte[] bytes = message.encode();
		byte[] fullMessage = new byte[bytes.length + 1];
		fullMessage[0] = message.getType().getId();
		System.arraycopy(bytes, 0, fullMessage, 1, bytes.length);
		send(fullMessage);
	}

	/**
	 * Returns the next ID to use for {@code #ask}.
	 *
	 * @return the next ID to use for ask messages
	 */
	byte nextAskId();

	/**
	 * Sends a raw array of bytes to the connected server and returns a future that will be
	 * completed upon receiving a response.
	 *
	 * @param data the byte array to send
	 * @return a future that will be completed upon receiving a response or throw a
	 * {@link java.util.concurrent.TimeoutException TimeoutException} if a response is not received
	 */
	@NotNull
	CompletableFuture<ReplyMessage> ask(byte @NotNull [] data);

	/**
	 * Sends a message to the connected server and returns a future that will be completed upon
	 * receiving a response.
	 *
	 * @param message the message to send
	 * @return a future that will be completed upon receiving a response or throw a
	 * {@link java.util.concurrent.TimeoutException TimeoutException} if a response is not received
	 */
	@NotNull
	default CompletableFuture<ReplyMessage> ask(@NotNull AskMessage message) {
		byte[] bytes = message.encode();
		byte[] fullMessage = new byte[bytes.length + 2];
		fullMessage[0] = message.getType().getId();
		fullMessage[1] = nextAskId();
		System.arraycopy(bytes, 0, fullMessage, 2, bytes.length);
		return ask(fullMessage);
	}

	/**
	 * Registers a handler for incoming messages of the given type.
	 *
	 * @param type    the type of message to handle
	 * @param handler the handler to register
	 */
	<M extends VoidMessage> void registerHandler(@NotNull MessageType<M> type, @NotNull Consumer<M> handler);

	/**
	 * Registers a handler for incoming ask messages of the given type.
	 *
	 * @param type    the type of ask message to handle
	 * @param handler the handler to register
	 */
	<M extends AskMessage> void registerHandler(@NotNull MessageType<M> type, @NotNull Function<M, ReplyMessage> handler);
}
