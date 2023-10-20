package dev.qixils.aviary.minigames.utils

import dev.qixils.aviary.db.Identifiable
import org.bukkit.entity.Player
import java.util.UUID

fun Player.identifiableID(): Identifiable<UUID> = object : Identifiable<UUID> {
	override val id: UUID
		get() = uniqueId
}
