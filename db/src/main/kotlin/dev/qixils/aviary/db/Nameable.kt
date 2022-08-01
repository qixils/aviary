package dev.qixils.aviary.db

/**
 * An object that is identifiable by a unique identifier
 * and has a fixed human-readable display name.
 */
interface FixedNameable {

	/**
	 * The human-readable display name of this object.
	 */
	val name: String
}

/**
 * An object that is identifiable by a unique identifier
 * and has a customizable human-readable display name.
 */
interface VariableNameable : FixedNameable {

	/**
	 * The human-readable display name of this object.
	 */
	override var name: String
}
