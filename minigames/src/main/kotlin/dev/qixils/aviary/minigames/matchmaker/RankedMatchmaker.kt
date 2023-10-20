package dev.qixils.aviary.minigames.matchmaker

import dev.qixils.aviary.minigames.match.MatchType
import org.bukkit.entity.Player
import java.util.UUID

class RankedMatchmaker(matchType: MatchType) : AbstractMatchmaker(matchType) {
	private val rankings = mutableMapOf<UUID, Int>()

	override fun addPlayer(player: Player) {
		super.addPlayer(player)
		rankings[player.uniqueId] = 0 // todo: db call
	}

	override fun removePlayer(uuid: UUID) {
		super.removePlayer(uuid)
		rankings.remove(uuid)
	}

	override fun tick() {
		TODO("Not yet implemented")
	}
}
