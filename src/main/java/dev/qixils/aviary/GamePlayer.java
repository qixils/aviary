package dev.qixils.aviary;

import dev.qixils.aviary.match.Match;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A player in a match.
 */
public final class GamePlayer implements ForwardingAudience.Single {
	private final @NotNull Player player;
	private final @NotNull Match<?> match;
	private @Nullable GamePlayerState state;
	private @Nullable Team team;
	private int score;

	/**
	 * Initializes a game player given its player.
	 *
	 * @param player the player
	 * @param match the match
	 */
	public GamePlayer(@NotNull Player player, @NotNull Match<?> match) {
		this.player = player;
		this.match = match;
	}

	/**
	 * Returns the wrapped {@link Player} associated with this game player.
	 *
	 * @return server player
	 */
	public @NotNull Player getPlayer() {
		return player;
	}

	@Override
	public @NotNull Audience audience() {
		return player;
	}

	/**
	 * Returns the match that this player is participating in.
	 *
	 * @return the match
	 */
	public @NotNull Match<?> getMatch() {
		return match;
	}

	/**
	 * Returns the state of this player.
	 *
	 * @return the state
	 */
	public @NotNull GamePlayerState getState() {
		return Objects.requireNonNullElse(state, GamePlayerState.LOBBY);
	}

	/**
	 * Sets the state of this player.
	 *
	 * @param state the state
	 */
	public void setState(@NotNull GamePlayerState state) {
		this.state = state;
	}

	/**
	 * Returns the team this player is on.
	 * May be {@code null} if the player is not {@link GamePlayerState#PLAYING PLAYING}.
	 *
	 * @return the team
	 */
	public @Nullable Team getTeam() {
		return team;
	}

	/**
	 * Sets the team this player is on.
	 *
	 * @param team the team
	 */
	public void setTeam(@Nullable Team team) {
		this.team = team;
	}

	/**
	 * Returns this player's score in the game.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets this player's score in the game.
	 *
	 * @param score the score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Updates this player's score in the game and returns the new score.
	 *
	 * @param delta the delta
	 * @return the new score
	 */
	public int updateAndGetScore(int delta) {
		return score += delta;
	}

	/**
	 * Updates this player's score in the game and returns the old score.
	 *
	 * @param delta the delta
	 * @return the old score
	 */
	public int getAndUpdateScore(int delta) {
		int oldScore = score;
		score += delta;
		return oldScore;
	}

	/**
	 * The state of a player.
	 */
	public enum GamePlayerState {
		/**
		 * The player is waiting in a lobby for a match to start.
		 */
		LOBBY,
		/**
		 * The player is playing a match.
		 */
		PLAYING,
		/**
		 * The player is spectating a match.
		 */
		SPECTATING
	}
}
