package dev.qixils.aviary.db

/**
 * An object that is identifiable by a unique identifier.
 *
 * @param I the type of identifier
 */
interface Identifiable<I : Any> {

	/**
	 * The value that uniquely identifies this object.
	 */
	val id: I
}
