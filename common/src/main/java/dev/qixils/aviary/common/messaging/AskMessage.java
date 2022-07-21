package dev.qixils.aviary.common.messaging;

/**
 * An object that is sent to the connected server and expects a reply.
 *
 * @param <R> the type of the expected reply
 */
public interface AskMessage<R extends Message> extends Message {
}
