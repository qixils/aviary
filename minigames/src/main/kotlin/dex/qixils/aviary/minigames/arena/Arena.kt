package dex.qixils.aviary.minigames.arena

import dev.qixils.aviary.db.Identifiable
import dev.qixils.aviary.db.VariableNameable
import dex.qixils.aviary.minigames.GameType
import dex.qixils.aviary.minigames.Minigames
import dex.qixils.aviary.minigames.Team
import dex.qixils.aviary.minigames.match.Match
import net.minestom.server.color.DyeColor
import net.minestom.server.coordinate.Pos
import net.minestom.server.instance.AnvilLoader
import net.minestom.server.instance.InstanceContainer
import org.bson.codecs.pojo.annotations.*

// TODO:
//  - saving
//  - loading
//  - note: i'm not adding spectate or respawn positions because ideally i'd like to just hide
//    players when they die, give them fly, then turn fly off and tp them to spawn once their
//    respawn timer is up (or keep fly on and give them a spectate compass if super dead).

/**
 * Represents an arena on which players can fight.
 */
@BsonDiscriminator(value = "arena")
open class Arena @BsonCreator private constructor(

	@field:BsonId @param:BsonId
	override val id: String,

	@field:BsonProperty("name") @param:BsonProperty("name")
	override var name: String,

	@field:BsonProperty("gameType") @param:BsonProperty("gameType")
	private val gameTypeId: String,

	@field:BsonProperty("teams") @param:BsonProperty("teams")
	val teams: MutableList<Team>,

	@field:BsonProperty("spawnPoints") @param:BsonProperty("spawnPoints")
	val spawnPoints: MutableList<Pos>,

	@field:BsonProperty("lobby") @param:BsonProperty("lobby")
	var lobby: Pos?,

) : VariableNameable, Identifiable<String> {

	@delegate:BsonIgnore
	private val instance: InstanceContainer by lazy {
		Minigames.instanceManager.createInstanceContainer(AnvilLoader(id))
	}

	/**
	 * Initializes an arena from another arena.
	 * Intended for updating the mechanic of an arena.
	 *
	 * @param arena        the arena to copy
	 * @param newGameTypeId the new game type's unique identifier
	 */
	constructor(arena: Arena, newGameTypeId: String) : this(
		arena.id,
		newGameTypeId,
		arena.name,
		arena.teams.toMutableList(),
		arena.spawnPoints.toMutableList(),
		arena.lobby
	)

	/**
	 * The [GameType] of the arena.
	 */
	val gameType: GameType<*, *>
		get() = GameType[gameTypeId]
		// no setter because the constructor should be used instead

	/**
	 * Creates a new match from this arena.
	 *
	 * @return new match
	 */
	fun createMatch(): Match<*> {
		return (gameType as GameType<Arena, *>).createMatch(this)
	}

	/**
	 * Loads the arena's instance.
	 *
	 * @param copy whether to create a read-only copy of the instance or not
	 * @return the arena's instance
	 */
	fun loadInstance(copy: Boolean): InstanceContainer {
		if (!copy)
			return instance
		val instance: InstanceContainer = instance.copy()
		instance.setTag(Minigames.DISABLE_SAVING_TAG, true)
		return instance
	}

	/**
	 * Saves the arena to the database.
	 */
	fun save() {
		// TODO
	}

	companion object {
		/**
		 * Creates a new arena.
		 *
		 * @param id          the arena's unique identifier
		 * @param gameType    the arena's game type
		 * @param name        the arena's name
		 * @param teams       the arena's teams
		 * @param spawnPoints the arena's spawn points
		 * @param lobby       the arena's lobby
		 */
		fun create(
			id: String,
			gameType: GameType<*, *>,
			name: String = id,
			teams: List<Team> = mutableListOf(Team("Default", DyeColor.WHITE)),
			spawnPoints: List<Pos> = mutableListOf(),
			lobby: Pos? = null
		): Arena {
			// TODO: check if arena with this ID exists already
			return Arena(id, gameType.id, name, teams.toMutableList(), spawnPoints.toMutableList(), lobby)
		}
	}
}
