package dex.qixils.aviary.match;

import dex.qixils.aviary.GamePlayer;
import dex.qixils.aviary.Identifiable;
import dex.qixils.aviary.Team;
import dex.qixils.aviary.arena.Arena;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// TODO: don't forget to "close" the instance when the match is over
//  (MinecraftServer.getInstanceManager().unregisterInstance(InstanceContainer))
//  (note: instance's chunk loader i think doesn't need to be closed entirely as it's used by other
//  matches on the same arena)

/**
 * A match is an active game that players are participating in.
 * It stores data about the game and manages its state.
 *
 * @param <A> the type of arena used for this game type
 */
public class Match<A extends Arena> implements ForwardingAudience, Identifiable<UUID> {
	private final @NotNull UUID uuid = UUID.randomUUID();
	private final @NotNull A arena;
	private final @NotNull List<GamePlayer> players = new ArrayList<>();
	private final @NotNull Instance instance;
	private @NotNull MatchState state = MatchState.LOBBY;

	/**
	 * Initializes a match given its arena.
	 *
	 * @param arena the arena used for this match
	 */
	public Match(@NotNull A arena) {
		this.arena = arena;
		this.instance = arena.loadInstance(true);
	}

	@Override
	public @NotNull UUID getId() {
		return uuid;
	}

	/**
	 * Adds a player to the match.
	 *
	 * @param player the player to add
	 */
	public void addPlayer(@NotNull Player player) {
		players.add(new GamePlayer(player, this));
	}

	/**
	 * Gets the players in the match.
	 *
	 * @return the players in the match
	 */
	public @NotNull List<GamePlayer> getPlayers() {
		return players;
	}

	@Override
	public @NotNull Iterable<? extends Audience> audiences() {
		return players;
	}

	/**
	 * Gets the active teams and their players.
	 * May be empty if the match is not {@link MatchState#ACTIVE ACTIVE}.
	 *
	 * @return the teams in the match
	 */
	public @NotNull Map<Team, List<GamePlayer>> getTeams() {
		Map<Team, List<GamePlayer>> teams = new HashMap<>();
		for (GamePlayer player : players)
			teams.computeIfAbsent(player.getTeam(), t -> new ArrayList<>()).add(player);
		return teams;
	}

	/**
	 * Gets the arena used for this match.
	 *
	 * @return the arena used for this match
	 */
	public @NotNull A getArena() {
		return arena;
	}

	/**
	 * Gets the state of the match.
	 *
	 * @return the state of the match
	 */
	public @NotNull MatchState getState() {
		return state;
	}

	/**
	 * Sets the state of the match.
	 *
	 * @param state the state of the match
	 */
	public void setState(@NotNull MatchState state) {
		this.state = state;
	}

	/**
	 * Gets the instance used for this match.
	 *
	 * @return the instance used for this match
	 */
	public @NotNull Instance getInstance() {
		return instance;
	}

	/**
	 * The state of a match.
	 */
	public enum MatchState {
		/**
		 * The match is waiting to start.
		 */
		LOBBY,
		/**
		 * The match is currently running.
		 */
		ACTIVE
	}
}
