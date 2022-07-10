package dex.qixils.aviary.minigames;

import org.jetbrains.annotations.NotNull;

// TODO implement adventure's Identified

/**
 * An object that is identifiable by a unique identifier.
 *
 * @param <I> the type of identifier
 */
public interface Identifiable<I> {

	/**
	 * Returns the value that uniquely identifies this object.
	 *
	 * @return unique identifier
	 */
	@NotNull I getId();
}
