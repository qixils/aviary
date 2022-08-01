package dex.qixils.aviary.minigames.command

import dex.qixils.aviary.minigames.Minigames
import dex.qixils.aviary.minigames.utils.AnvilPrompt
import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player

class ArenaCreateCommand : Command("create") {
	init {
		setDefaultExecutor { sender, _ ->
			if (sender !is Player) {
				Minigames.alert(sender, "This command can only be executed by players.")
				return@setDefaultExecutor
			}

			AnvilPrompt.stringBuilder()
				.player(sender)
				.prompt(Component.text("Please enter the ID of the arena to create"))
				.build()
				.thenAccept { name ->
					// TODO: Prompt for game type
					// TODO: Create arena
				}
		}
	}
}
