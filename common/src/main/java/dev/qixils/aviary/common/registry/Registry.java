package dev.qixils.aviary.common.registry;

import dev.qixils.aviary.db.Identifiable;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A collection of registered objects.
 *
 * @param <T> the type of object registered
 */
public final class Registry<T> implements Identifiable<Key> {
	private final Key id;
	private final Map<Key, T> map = new HashMap<>();

	/**
	 * Creates a new registry with the given ID.
	 *
	 * @param id the ID of the registry
	 */
	public Registry(@NotNull Key id) {
		this.id = id;
	}

	/**
	 * Gets the ID of this registry.
	 *
	 * @return the ID of this registry
	 */
	@NotNull
	@Override
	public Key getId() {
		return id;
	}

	/**
	 * Registers the given object with the given ID.
	 *
	 * @param id the ID of the object
	 * @param object the object to register
	 */
	public void register(@NotNull Key id, @NotNull T object) {
		if (map.containsKey(id)) {
			throw new IllegalArgumentException(id.asString() + " is already registered");
		}
		map.put(id, object);
	}

	/**
	 * Returns a view of the registered objects.
	 * The returned view is immutable and will reflect changes to the registry.
	 *
	 * @return a view of the registered objects
	 */
	@NotNull
	public Collection<T> values() {
		return Collections.unmodifiableCollection(map.values());
	}

	/**
	 * Returns the object registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @return the object registered with the given ID or {@code null} if no object is registered
	 *         with the given ID
	 */
	@Nullable
	public T get(@NotNull Key id) {
		return map.get(id);
	}

	/**
	 * Returns an optional of the object registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @return the object registered with the given ID or {@link Optional#empty()} if no object is
	 *         registered with the given ID
	 */
	@NotNull
	public Optional<T> getOptional(@NotNull Key id) {
		return Optional.ofNullable(get(id));
	}

	/**
	 * Returns the object registered with the given ID or the default value if no object is
	 * registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @param defaultValue the default value to return if no object is registered with the given ID
	 * @return the object registered with the given ID or the default value if no object is
	 *         registered with the given ID
	 */
	@Contract("_, !null -> !null")
	public T getOrDefault(@NotNull Key id, @Nullable T defaultValue) {
		return map.getOrDefault(id, defaultValue);
	}

	/**
	 * Returns the object registered with the given ID or the default value if no object is
	 * registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @param defaultValueSupplier the supplier of the default value to return if no object is
	 *                             registered with the given ID
	 * @return the object registered with the given ID or the default value if no object is
	 *         registered with the given ID
	 */
	@UnknownNullability("defaultValueSupplier may return null")
	public T getOrDefault(@NotNull Key id, @NotNull Supplier<T> defaultValueSupplier) {
		return map.getOrDefault(id, defaultValueSupplier.get());
	}

	/**
	 * Returns the object registered with the given ID or throws an exception if no object is
	 * registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @return the object registered with the given ID
	 * @throws IllegalArgumentException if no object is registered with the given ID
	 */
	@NotNull
	public T getRequired(@NotNull Key id) {
		T object = get(id);
		if (object == null) {
			throw new IllegalArgumentException(id.asString() + " is not registered");
		}
		return object;
	}

	/**
	 * Returns an immutable view of the registered objects and their IDs.
	 * This view will reflect changes to the registry.
	 *
	 * @return an immutable view of the registered objects and their IDs
	 */
	@NotNull
	public Map<Key, T> asMap() {
		return Collections.unmodifiableMap(map);
	}

	/**
	 * Determines if the registry contains an object with the given ID.
	 *
	 * @param id the ID of the object
	 * @return {@code true} if the registry contains an object with the given ID
	 */
	public boolean contains(@NotNull Key id) {
		return map.containsKey(id);
	}
}
