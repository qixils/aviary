package dex.qixils.aviary.minigames;

import net.minestom.server.color.DyeColor;
import net.minestom.server.coordinate.Pos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * A team is what unites a group of players to fight together.
 */
public final class Team implements Nameable<UUID> {
	private final @NotNull UUID uuid;
	private @Nullable String name;
	private @NotNull DyeColor color;
	private @NotNull UUID loadoutUuid;
	private @NotNull List<Pos> spawnPoints;

	// TODO constructors

	@Override
	public @NotNull UUID getId() {
		return uuid;
	}

	@Override
	public @NotNull String getName() {
		return name;
	}

	@Override
	public void setName(@NotNull String name) {
		this.name = name;
	}

	/**
	 * Gets the {@link Loadout} applied to players on this team.
	 *
	 * @return loadout
	 */
	public @NotNull Loadout getLoadout() {
		return Loadout.load(loadoutUuid);
	}
}
