package dev.qixils.aviary.minigames.matchmaker

import dev.qixils.aviary.minigames.match.MatchType
import dev.qixils.aviary.minigames.utils.removeFirst
import dev.qixils.aviary.minigames.utils.segment
import dev.qixils.aviary.minigames.utils.split

class BasicMatchmaker(matchType: MatchType) : AbstractMatchmaker(matchType) {
	override fun tick() {
		// Note: player list is naturally sorted by longest waiting time
		// This loop creates a match if the max player count is reached or if the first player is waiting for too long (and the min player count is reached)
		while (players.size >= matchType.players.first && (players.size >= matchType.players.last || players.first().waitingTime >= forceMatchAt)) {
			matchType.createMatch(players.removeFirst(matchType.players.last))
		}
	}
}
