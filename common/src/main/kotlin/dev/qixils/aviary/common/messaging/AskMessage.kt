package dev.qixils.aviary.common.messaging

/**
 * An object that is sent to the connected server and expects a reply.
 *
 * @param R The type of the reply.
*/
interface AskMessage<R : Message> : Message
