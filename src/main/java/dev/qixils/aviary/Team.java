package dev.qixils.aviary;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class Team implements Nameable<UUID> {
	private final @NotNull UUID uuid;
	private @NotNull String name;
	private @NotNull UUID loadoutUuid;

	// TODO constructors

	@Override
	public @NotNull UUID getId() {
		return uuid;
	}

	@Override
	public @NotNull String getName() {
		return name;
	}

	public @NotNull Loadout getLoadout() {
		return Loadout.load(loadoutUuid);
	}
}
