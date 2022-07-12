package dev.qixils.aviary.db;

import org.jetbrains.annotations.NotNull;

/**
 * An object that is identifiable by a unique identifier and has a human-readable display name.
 *
 * @param <I> the type of identifier
 */
public interface Nameable<I> extends Identifiable<I> {
	/**
	 * Gets the human-readable display name of this object.
	 *
	 * @return display name
	 */
	@NotNull String getName();

	/**
	 * Sets the human-readable display name of this object.
	 *
	 * @param name the new display name
	 */
	void setName(@NotNull String name);
}
