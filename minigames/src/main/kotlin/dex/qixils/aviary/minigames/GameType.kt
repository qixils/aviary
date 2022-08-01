package dex.qixils.aviary.minigames

import dev.qixils.aviary.db.Identifiable
import dex.qixils.aviary.minigames.arena.Arena
import dex.qixils.aviary.minigames.match.Match
import java.util.*
import java.util.function.BiFunction
import java.util.function.Function

/**
 * An enum-like class that stores information about each game type.
 *
 * @param A the type of arena used for this game type
 * @param M the type of match used for this game type
 */
class GameType<A : Arena, out M : Match<A>>(
	/**
	 * The fixed ID of the game type.
	 */
	override val id: String,
	/**
	 * The display name of the game type.
	 */
	val name: String,

	private val arenaCreator: BiFunction<String, GameType<A, M>, A>,
	private val arenaUpdater: BiFunction<Arena, GameType<A, M>, A>,
	private val matchCreator: Function<A, M>
) : Identifiable<String> {

	/**
	 * Returns a new arena instance.
	 *
	 * @param id the arena's unique identifier
	 * @return a new arena instance
	 */
	fun createArena(id: String): A {
		return arenaCreator.apply(id, this)
	}

	/**
	 * Updates an arena instance.
	 *
	 * @param arena the arena to update
	 * @return the updated arena instance
	 */
	fun updateArena(arena: Arena): A {
		return arenaUpdater.apply(arena, this).apply { save() }
	}

	/**
	 * Returns a new match instance.
	 *
	 * @param arena the arena to use for the match
	 * @return a new match instance
	 */
	fun createMatch(arena: A): M {
		return matchCreator.apply(arena)
	}

	companion object {
		// registry
		private val REGISTRY: MutableMap<String, GameType<*, *>> = HashMap()
		private fun <A : Arena, M : Match<A>> register(
			id: String,
			name: String,
			arenaCreator: BiFunction<String, GameType<A, M>, A>,
			arenaUpdater: BiFunction<Arena, GameType<A, M>, A>,
			matchCreator: Function<A, M>
		): GameType<A, M> {
			val gameType = GameType(id, name, arenaCreator, arenaUpdater, matchCreator)
			REGISTRY[id] = gameType
			return gameType
		}

		operator fun get(id: String): GameType<*, *> {
			return Objects.requireNonNull(REGISTRY[id], "GameType not found: $id")!!
		}
	}
}
