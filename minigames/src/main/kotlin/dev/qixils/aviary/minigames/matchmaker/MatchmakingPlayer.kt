package dev.qixils.aviary.minigames.matchmaker

import dev.qixils.aviary.db.Identifiable
import org.bukkit.entity.Player
import java.time.Duration
import java.time.Instant
import java.util.UUID

data class MatchmakingPlayer(
	val player: Player,
	val matchmaker: AbstractMatchmaker,
	val started: Instant
) : Identifiable<UUID> {
	constructor(player: Player, matchmaker: AbstractMatchmaker) : this(player, matchmaker, Instant.now())

	override val id: UUID
		get() = player.uniqueId

	val waitingTime: Duration
		get() = Duration.between(started, Instant.now())
}
