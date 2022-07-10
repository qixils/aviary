package dex.qixils.aviary;

import dex.qixils.aviary.arena.Arena;
import dex.qixils.aviary.match.Match;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An enum-like class that stores information about each game type.
 *
 * @param <A> the type of arena used for this game type
 * @param <M> the type of match used for this game type
 */
public final class GameType<A extends Arena, M extends Match<A>> {
	private final @NotNull String id;
	private final @NotNull String name;
	private final @NotNull BiFunction<String, GameType<A, M>, A> arenaCreator;
	private final @NotNull BiFunction<Arena, GameType<A, M>, A> arenaUpdater;
	private final @NotNull Function<A, M> matchCreator;

	/**
	 * Instantiates a new game type.
	 *
	 * @param id the game type's unique identifier
	 * @param name the display name of the game type
	 * @param arenaCreator the function that creates an arena from its id
	 * @param arenaUpdater the function that creates an updated arena from an existing one
	 * @param matchCreator the function that creates a match from an arena
	 */
	private GameType(@NotNull String id,
					@NotNull String name,
					@NotNull BiFunction<String, GameType<A, M>, A> arenaCreator,
					@NotNull BiFunction<Arena, GameType<A, M>, A> arenaUpdater,
					@NotNull Function<A, M> matchCreator) {
		this.id = id;
		this.name = name;
		this.arenaCreator = arenaCreator;
		this.arenaUpdater = arenaUpdater;
		this.matchCreator = matchCreator;
	}

	/**
	 * Returns the fixed ID of the game type.
	 *
	 * @return unique identifier
	 */
	public @NotNull String getId() {
		return id;
	}

	/**
	 * Returns the display name of the game type.
	 *
	 * @return display name
	 */
	public @NotNull String getName() {
		return name;
	}

	/**
	 * Returns a new arena instance.
	 *
	 * @param id the arena's unique identifier
	 * @return a new arena instance
	 */
	public @NotNull A createArena(@NotNull String id) {
		return arenaCreator.apply(id, this);
	}

	/**
	 * Updates an arena instance.
	 *
	 * @param arena the arena to update
	 * @return the updated arena instance
	 */
	public @NotNull A updateArena(@NotNull Arena arena) {
		A newArena = arenaUpdater.apply(arena, this);
		newArena.save();
		return newArena;
	}

	/**
	 * Returns a new match instance.
	 *
	 * @param arena the arena to use for the match
	 * @return a new match instance
	 */
	public @NotNull M createMatch(@NotNull A arena) {
		return matchCreator.apply(arena);
	}

	// registry

	private static final @NotNull Map<String, GameType<?, ?>> REGISTRY = new HashMap<>();

	private static @NotNull <A extends Arena, M extends Match<A>> GameType<A, M> register(
			@NotNull String id,
			@NotNull String name,
			@NotNull BiFunction<String, GameType<A, M>, A> arenaCreator,
			@NotNull BiFunction<Arena, GameType<A, M>, A> arenaUpdater,
			@NotNull Function<A, M> matchCreator
	) {
		GameType<A, M> gameType = new GameType<>(id, name, arenaCreator, arenaUpdater, matchCreator);
		REGISTRY.put(id, gameType);
		return gameType;
	}

	public static @NotNull GameType<?, ?> get(@NotNull String id) {
		return Objects.requireNonNull(REGISTRY.get(id), "GameType not found: " + id);
	}
}
