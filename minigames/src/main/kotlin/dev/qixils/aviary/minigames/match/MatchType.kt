package dev.qixils.aviary.minigames.match

import dev.qixils.aviary.minigames.matchmaker.AbstractMatchmaker
import dev.qixils.aviary.minigames.matchmaker.MatchmakingPlayer
import dev.qixils.aviary.minigames.matchmaker.RankedMatchmaker
import kotlin.reflect.KClass

enum class MatchType(
	val match: KClass<out AbstractMatch>,
	val matchmaker: AbstractMatchmaker,
	val players: IntRange,
) {
	PARKOUR_DUEL(
		ParkourDuelMatch::class,
		RankedMatchmaker(PARKOUR_DUEL),
		2..2,
	);

	fun createMatch(players: List<MatchmakingPlayer>): AbstractMatch {
		val constructor = match.constructors.first { it.parameters.size == 1 && it.parameters[0].type == List::class }
		return constructor.call(players)
	}
}
