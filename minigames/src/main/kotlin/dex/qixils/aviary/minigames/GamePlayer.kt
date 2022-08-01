package dex.qixils.aviary.minigames

import dex.qixils.aviary.minigames.match.Match
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import net.minestom.server.entity.Player

/**
 * A player in a match.
 */
class GamePlayer(
	/**
	 * The wrapped [Player] associated with this game player.
	 */
	val player: Player,
	/**
	 * The match that this player is participating in.
	 */
	val match: Match<*>
) : ForwardingAudience.Single {

	override fun audience(): Audience {
		return player
	}

	/**
	 * The state of this player.
	 */
	var state: GamePlayerState = GamePlayerState.LOBBY

	/**
	 * The team this player is on.
	 * May be `null` if the player is not [PLAYING][GamePlayerState.PLAYING].
	 */
	var team: Team? = null

	/**
	 * This player's score in the game.
	 */
	var score = 0

	/**
	 * Updates this player's score in the game and returns the new score.
	 *
	 * @param delta the delta
	 * @return the new score
	 */
	fun updateAndGetScore(delta: Int): Int {
		return delta.let { score += it; score }
	}

	/**
	 * Updates this player's score in the game and returns the old score.
	 *
	 * @param delta the delta
	 * @return the old score
	 */
	fun getAndUpdateScore(delta: Int): Int {
		val oldScore = score
		score += delta
		return oldScore
	}
}

/**
 * The state of a player.
 */
enum class GamePlayerState {
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
