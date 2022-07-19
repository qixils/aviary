package dev.qixils.aviary.common.messaging;

import dev.qixils.aviary.db.Identifiable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

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
	 * Produces a reply to an incoming ask.
	 *
	 * @param ask the ask message to reply to
	 * @return the reply to the ask message
	 */
	// TODO: is this how i want this to work?
	//  i could do reflection stuff to automatically instantiate replies to asks
	//  or i could do more like an event listener system where the code has to register event listeners
	//  and all this isn't to mention the handling of non-ask messages
	@NotNull
	ReplyMessage reply(@NotNull AskMessage ask);
}
