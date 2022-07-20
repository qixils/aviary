package dev.qixils.aviary.common.messaging;

import dev.qixils.aviary.common.chat.ChatAskMessage;
import dev.qixils.aviary.common.messaging.impl.BooleanReplyMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A type of message that can be sent to or from the proxy server.
 */
public enum MessageType {
	// TODO: manually specifying bytes is probably not necessary
	CHAT_ASK((byte) 0x00, ChatAskMessage.class),
	BOOLEAN_REPLY((byte) 0x01, BooleanReplyMessage.class),
	;

	private static final Map<Byte, MessageType> BY_ID;
	private static final Map<Class<? extends Message>, MessageType> BY_CLASS;

	static {
		Map<Byte, MessageType> byId = new HashMap<>();
		Map<Class<? extends Message>, MessageType> byClass = new HashMap<>();
		for (MessageType type : values()) {
			byId.put(type.id, type);
			byClass.put(type.clazz, type);
		}
		BY_ID = Collections.unmodifiableMap(byId);
		BY_CLASS = Collections.unmodifiableMap(byClass);
	}

	private final byte id;
	private final Class<? extends Message> clazz;

	MessageType(byte id, Class<? extends Message> clazz) {
		this.id = id;
		this.clazz = clazz;
	}

	/**
	 * Gets the message type with the given id.
	 *
	 * @param id the id of the message type
	 * @return the message type with the given id
	 * @throws IllegalArgumentException if no message type with the given id exists
	 */
	@NotNull
	public static MessageType byId(byte id) {
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
	public static MessageType byClass(Class<? extends Message> clazz) {
		if (!BY_CLASS.containsKey(clazz)) {
			throw new IllegalArgumentException("No message type with class " + clazz);
		}
		return BY_CLASS.get(clazz);
	}

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
	public Class<? extends Message> getClazz() {
		return clazz;
	}
}
