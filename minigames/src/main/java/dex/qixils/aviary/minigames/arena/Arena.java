package dex.qixils.aviary.minigames.arena;

import dex.qixils.aviary.minigames.Nameable;
import dex.qixils.aviary.minigames.match.Match;
import dex.qixils.aviary.minigames.Minigames;
import dex.qixils.aviary.minigames.GameType;
import dex.qixils.aviary.minigames.Team;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

// TODO:
//  - saving
//  - loading
//  - note: i'm not adding spectate or respawn positions because ideally i'd like to just hide
//    players when they die, give them fly, then turn fly off and tp them to spawn once their
//    respawn timer is up (or keep fly on and give them a spectate compass if super dead).

/**
 * Represents an arena on which players can fight.
 */
public class Arena implements Nameable<String> {
	private volatile @Nullable InstanceContainer instanceCache;
	private final @NotNull String id;
	private final @NotNull String gameTypeId;
	private final @NotNull List<Team> teams;
	private final @NotNull List<Pos> spawnPoints;
	private @Nullable String name;
	private @Nullable Pos lobby;

	/**
	 * Initializes an arena given its id and game type.
	 *
	 * @param id         arena's unique identifier
	 * @param gameTypeId game type's unique identifier
	 */
	public Arena(@NotNull String id, @NotNull String gameTypeId) {
		this.id = id;
		this.gameTypeId = gameTypeId;
		teams = new ArrayList<>();
		spawnPoints = new ArrayList<>();
	}

	/**
	 * Initializes an arena from another arena.
	 * Intended for updating the mechanic of an arena.
	 *
	 * @param arena 		the arena to copy
	 * @param newGameTypeId the new game type's unique identifier
	 */
	public Arena(@NotNull Arena arena, @NotNull String newGameTypeId) {
		this.id = arena.id;
		this.gameTypeId = newGameTypeId;
		this.teams = new ArrayList<>(arena.teams);
		this.spawnPoints = new ArrayList<>(arena.spawnPoints);
		this.name = arena.name;
		this.lobby = arena.lobby;
	}

	@Override
	public @NotNull String getId() {
		return id;
	}

	@Override
	public @NotNull String getName() {
		return name == null ? id : name;
	}

	@Override
	public void setName(@NotNull String name) {
		this.name = name;
	}

	/**
	 * Gets the {@link GameType} of the arena.
	 *
	 * @return game type
	 */
	public @NotNull GameType<?, ?> getGameType() {
		return GameType.get(gameTypeId);
	}

	/**
	 * Creates a new match from this arena.
	 *
	 * @return new match
	 */
	public @NotNull Match<?> createMatch() {
		return ((GameType<Arena, ?>) getGameType()).createMatch(this);
	}

	/**
	 * Gets the teams of the arena.
	 *
	 * @return list of teams
	 */
	public @NotNull List<Team> getTeams() {
		return teams;
	}

	/**
	 * Gets the arena's global spawn points. These override any per-team spawn points.
	 * May be empty if the arena has no global spawn points.
	 *
	 * @return list of spawn points
	 */
	public @NotNull List<Pos> getSpawnPoints() {
		return spawnPoints;
	}

	/**
	 * Gets the lobby of the arena.
	 * May be {@code null} if undefined which should be considered as an error.
	 *
	 * @return lobby position
	 */
	public @Nullable Pos getLobby() {
		return lobby;
	}

	/**
	 * Loads the arena's instance.
	 *
	 * @param copy whether to create a read-only copy of the instance or not
	 * @return the arena's instance
	 */
	public @NotNull InstanceContainer loadInstance(boolean copy) {
		if (instanceCache == null)
			instanceCache = Minigames.getInstanceManager().createInstanceContainer(new AnvilLoader(id));
		assert instanceCache != null; // IntelliJ is drunk today
		if (!copy)
			return instanceCache;
		InstanceContainer instance = instanceCache.copy();
		instance.setTag(Minigames.DISABLE_SAVING_TAG, true);
		return instance;
	}

	/**
	 * Saves the arena to the database.
	 */
	public void save() {
		// TODO
	}
}
