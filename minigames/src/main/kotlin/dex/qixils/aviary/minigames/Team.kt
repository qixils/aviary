package dex.qixils.aviary.minigames

import dev.qixils.aviary.db.VariableNameable
import net.minestom.server.color.DyeColor
import net.minestom.server.coordinate.Pos

/**
 * A team is what unites a group of players to fight together.
 */
data class Team(
	override var name: String,
	var color: DyeColor,
	val spawnPoints: MutableList<Pos> = mutableListOf()
) : VariableNameable
