package dev.qixils.aviary.common.messaging;

import dev.qixils.aviary.common.chat.ChatAskMessage;
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A type of message that can be sent to or from the proxy server.
 */
public final class MessageType<M extends Message> {
	// registry maps
	private static final Map<Byte, MessageType<?>> BY_ID = new HashMap<>();
	private static final Map<Class<? extends Message>, MessageType<?>> BY_CLASS = new HashMap<>();

	// instances
	public static final MessageType<ChatAskMessage> CHAT_ASK = register((byte) 0x00, ChatAskMessage.class, PacketType.ASK);
	public static final MessageType<BooleanReplyMessage> BOOLEAN_REPLY = register((byte) 0x01, BooleanReplyMessage.class, PacketType.REPLY);

	// fields
	private final byte id;
	private final @NotNull Class<M> clazz;
	private final @NotNull PacketType packetType;
	private MessageType(byte id, @NotNull Class<M> clazz, @NotNull PacketType packetType) {
		this.id = id;
		this.clazz = clazz;
		this.packetType = packetType;
	}

	// registry methods
	private static <T extends Message> MessageType<T> register(byte id, @NotNull Class<T> clazz, @NotNull PacketType packetType) {
		MessageType<T> type = new MessageType<>(id, clazz, packetType);
		BY_ID.put(id, type);
		BY_CLASS.put(clazz, type);
		return type;
	}

	/**
	 * Gets the message type with the given id.
	 *
	 * @param id the id of the message type
	 * @return the message type with the given id
	 * @throws IllegalArgumentException if no message type with the given id exists
	 */
	@NotNull
	public static MessageType<?> byId(byte id) {
		if (!BY_ID.containsKey(id)) {
			throw new IllegalArgumentException("No message type with id " + id);
		}
		return BY_ID.get(id);
	}

	/**
	 * Gets the message type with the given class.
	 *
	 * @param clazz the class of the message type
	 * @return the message type with the given class
	 * @throws IllegalArgumentException if no message type with the given class exists
	 */
	@NotNull
	public static MessageType<?> byClass(Class<? extends Message> clazz) {
		if (!BY_CLASS.containsKey(clazz)) {
			throw new IllegalArgumentException("No message type with class " + clazz);
		}
		return BY_CLASS.get(clazz);
	}

	// instance methods
	/**
	 * Gets the id of this message type.
	 *
	 * @return the id of this message type
	 */
	public byte getId() {
		return id;
	}

	/**
	 * Gets the class of this message type.
	 *
	 * @return the class of this message type
	 */
	@NotNull
	public Class<M> getMessageClass() {
		return clazz;
	}

	/**
	 * Gets the packet type of this message type.
	 * This determines how the message should be handled by the connected server.
	 *
	 * @return the packet type of this message type
	 */
	@NotNull
	public PacketType getPacketType() {
		return packetType;
	}
}
