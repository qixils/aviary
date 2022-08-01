package dex.qixils.aviary.minigames.match

import dev.qixils.aviary.db.Identifiable
import dex.qixils.aviary.minigames.GamePlayer
import dex.qixils.aviary.minigames.Team
import dex.qixils.aviary.minigames.arena.Arena
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import java.util.*

// TODO: don't forget to "close" the instance when the match is over
//  (MinecraftServer.getInstanceManager().unregisterInstance(InstanceContainer))
//  (note: instance's chunk loader I think doesn't need to be closed entirely as it's used by other
//  matches on the same arena)
/**
 * A match is an active game that players are participating in.
 * It stores data about the game and manages its state.
 *
 * @param A the type of arena used for this game type
 */
open class Match<A : Arena>(
	/**
	 * The arena used for this match.
	 */
	val arena: A
) : ForwardingAudience, Identifiable<UUID> {

	override val id = UUID.randomUUID()

	protected val players: MutableList<GamePlayer> = ArrayList()

	/**
	 * A copy of the list of players in this match.
	 */
	val playerView: List<GamePlayer> get() = players.toList()

	/**
	 * The instance in which this match is taking place.
	 */
	val instance: Instance = arena.loadInstance(true)

	/**
	 * The state of the match.
	 */
	var state = MatchState.LOBBY

	/**
	 * The active teams and their players.
	 * May be empty if the match is not [ACTIVE][MatchState.ACTIVE].
	 */
	val teams: Map<Team?, MutableList<GamePlayer>>
		get() {
			val teams: MutableMap<Team?, MutableList<GamePlayer>> = HashMap()
			players.forEach { player ->
				teams.getOrPut(player.team) { ArrayList() }.add(player)
			}
			return teams
		}

	/**
	 * Adds a player to the match.
	 *
	 * @param player the player to add
	 */
	fun addPlayer(player: Player) {
		players.add(GamePlayer(player, this))
		player.setInstance(instance, arena.lobby!!)
	}

	override fun audiences(): Iterable<Audience> {
		return playerView
	}
}

/**
 * The state of a match.
 */
enum class MatchState {
	/**
	 * The match is waiting to start.
	 */
	LOBBY,

	/**
	 * The match is currently running.
	 */
	ACTIVE
}
