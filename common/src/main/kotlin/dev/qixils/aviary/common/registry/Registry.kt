package dev.qixils.aviary.common.registry

import dev.qixils.aviary.db.Identifiable
import net.kyori.adventure.key.Key
import org.jetbrains.annotations.Contract
import java.util.*
import java.util.function.Supplier

/**
 * A collection of registered objects.
 *
 * @param T the type of object registered
 */
class Registry<T : Any>(override val id: Key) : Identifiable<Key> {

	private val map: MutableMap<Key, T> = HashMap()

	/**
	 * Registers the given object with the given ID.
	 *
	 * @param id  the ID of the object
	 * @param obj the object to register
	 */
	fun register(id: Key, obj: T) {
		require(!map.containsKey(id)) { id.asString() + " is already registered" }
		map[id] = obj
	}

	/**
	 * Returns a view of the registered objects.
	 * The returned view is immutable and will reflect changes to the registry.
	 *
	 * @return a view of the registered objects
	 */
	fun values(): Collection<T> {
		return Collections.unmodifiableCollection(map.values)
	}

	/**
	 * Returns the object registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @return the object registered with the given ID or `null` if no object is registered
	 * with the given ID
	 */
	operator fun get(id: Key): T? {
		return map[id]
	}

	/**
	 * Returns an optional of the object registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @return the object registered with the given ID or [Optional.empty] if no object is
	 * registered with the given ID
	 */
	fun getOptional(id: Key): Optional<T> {
		return Optional.ofNullable(get(id))
	}

	/**
	 * Returns the object registered with the given ID or the default value if no object is
	 * registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @param defaultValue the default value to return if no object is registered with the given ID
	 * @return the object registered with the given ID or the default value if no object is
	 * registered with the given ID
	 */
	@Contract("_, !null -> !null")
	fun getOrDefault(id: Key, defaultValue: T): T {
		return map.getOrDefault(id, defaultValue)
	}

	/**
	 * Returns the object registered with the given ID or the default value if no object is
	 * registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @param defaultValueSupplier the supplier of the default value to return if no object is
	 * registered with the given ID
	 * @return the object registered with the given ID or the default value if no object is
	 * registered with the given ID
	 */
	fun getOrDefault(
		id: Key,
		defaultValueSupplier: Supplier<T>
	): T {
		return map.getOrDefault(id, defaultValueSupplier.get())
	}

	/**
	 * Returns the object registered with the given ID or throws an exception if no object is
	 * registered with the given ID.
	 *
	 * @param id the ID of the object
	 * @return the object registered with the given ID
	 * @throws IllegalArgumentException if no object is registered with the given ID
	 */
	fun getRequired(id: Key): T {
		return requireNotNull(get(id)) { id.asString() + " is not registered" }
	}

	/**
	 * Returns an immutable view of the registered objects and their IDs.
	 * This view will reflect changes to the registry.
	 *
	 * @return an immutable view of the registered objects and their IDs
	 */
	fun asMap(): Map<Key, T> {
		return Collections.unmodifiableMap(map)
	}

	/**
	 * Determines if the registry contains an object with the given ID.
	 *
	 * @param id the ID of the object
	 * @return `true` if the registry contains an object with the given ID
	 */
	operator fun contains(id: Key): Boolean {
		return map.containsKey(id)
	}
}
