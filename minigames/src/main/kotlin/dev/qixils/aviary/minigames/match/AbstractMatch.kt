package dev.qixils.aviary.minigames.match

import dev.qixils.aviary.db.Identifiable
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import org.bukkit.entity.Player
import java.util.UUID

abstract class AbstractMatch : ForwardingAudience, Identifiable<UUID> {

	override val id = UUID.randomUUID()

	protected val players = mutableListOf<Player>()
	override fun audiences(): MutableIterable<Audience> = players


}
