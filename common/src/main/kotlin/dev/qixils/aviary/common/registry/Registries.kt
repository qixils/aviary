package dev.qixils.aviary.common.registry

import dev.qixils.aviary.common.Aviary
import net.kyori.adventure.key.Key

object Registries {

	private fun initMetaRegistry(): Registry<Registry<*>> {
		val registry = Registry<Registry<*>>(Key.key(Aviary.NAMESPACE, "registry"))
		registry.register(registry.id, registry)
		return registry
	}

	fun <T : Any> create(id: Key): Registry<T> {
		val registry = Registry<T>(id)
		REGISTRIES.register(id, registry)
		return registry
	}

	fun <T : Any> create(id: String): Registry<T> {
		return create(Key.key(Aviary.NAMESPACE, id))
	}

	var REGISTRIES = initMetaRegistry()
}
