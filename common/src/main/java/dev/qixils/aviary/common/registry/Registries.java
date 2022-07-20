package dev.qixils.aviary.common.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import static dev.qixils.aviary.common.Aviary.NAMESPACE;

public final class Registries {
	private Registries() {
		throw new UnsupportedOperationException("This class is not meant to be instantiated");
	}

	@NotNull
	private static Registry<Registry<?>> initMetaRegistry() {
		Registry<Registry<?>> registry = new Registry<>(Key.key(NAMESPACE, "registry"));
		registry.register(registry.getId(), registry);
		return registry;
	}

	@NotNull
	public static <T> Registry<T> create(@NotNull Key id) {
		Registry<T> registry = new Registry<>(id);
		REGISTRIES.register(id, registry);
		return registry;
	}

	@NotNull
	public static <T> Registry<T> create(@NotNull String id) {
		return create(Key.key(NAMESPACE, id));
	}

	public static Registry<Registry<?>> REGISTRIES = initMetaRegistry();
}
