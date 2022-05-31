package dev.qixils.aviary.arena;

import dev.qixils.aviary.Aviary;
import dev.qixils.aviary.GameType;
import dev.qixils.aviary.Nameable;
import dev.qixils.aviary.Team;
import dev.qixils.aviary.match.Match;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// TODO:
//  - teams
//  - loadouts
//  - instance?
//  - saving
//  - loading

/**
 * Represents an arena on which players can fight.
 */
public class Arena implements Nameable<String> {
	private final @NotNull String id;
	private final @NotNull String gameTypeId;
	private final @NotNull List<UUID> teams;
	private @Nullable String name;

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
	}

	/**
	 * Initializes an arena from another arena.
	 * Intended for updating the mechanic of an arena.
	 *
	 * @param arena the arena to copy
	 */
	public Arena(@NotNull Arena arena, @NotNull String newGameTypeId) {
		this.id = arena.id;
		this.gameTypeId = newGameTypeId;
		this.teams = new ArrayList<>(arena.teams);
		this.name = arena.name;
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
		return Collections.emptyList(); // TODO load teams from database
	}

	/**
	 * Loads the arena's instance.
	 *
	 * @param copy whether to create a read-only copy of the instance or not
	 * @return the arena's instance
	 */
	public @NotNull InstanceContainer loadInstance(boolean copy) {
		InstanceContainer instance = Aviary.getInstanceManager().createInstanceContainer(new AnvilLoader(id));
		instance.setTag(Aviary.DISABLE_SAVING_TAG, copy);
		return instance;
	}

	/**
	 * Saves the arena to the database.
	 */
	public void save() {
		// TODO
	}
}
