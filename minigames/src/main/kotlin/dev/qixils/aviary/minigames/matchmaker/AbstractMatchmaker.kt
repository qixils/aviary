package dev.qixils.aviary.minigames.matchmaker

import dev.qixils.aviary.minigames.match.MatchType
import org.bukkit.entity.Player
import java.time.Duration
import java.util.*

abstract class AbstractMatchmaker(protected val matchType: MatchType) {
	protected val forceMatchAt: Duration = Duration.ofSeconds(30)
	protected val players = mutableListOf<MatchmakingPlayer>()

	open fun addPlayer(player: Player) {
		players.add(MatchmakingPlayer(player, this))
	}

	open fun removePlayer(uuid: UUID) {
		players.removeIf { it.id == uuid }
	}

	abstract fun tick()
}
