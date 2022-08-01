package dex.qixils.aviary.minigames.command

import dex.qixils.aviary.minigames.Minigames
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Entity

class SaveInstanceCommand : Command("save-instance") {
	init {
		setDefaultExecutor { sender, _ ->
			if (sender !is Entity) {
				Minigames.alert(sender, "This command can only be executed by physical entities.")
				return@setDefaultExecutor
			}
			val instance = sender.instance;
			if (instance == null) {
				Minigames.alert(sender, "You are not in an instance.")
				return@setDefaultExecutor
			}
			if (instance.getTag(Minigames.DISABLE_SAVING_TAG)) {
				Minigames.alert(sender, "This instance cannot be saved to disk.")
				return@setDefaultExecutor
			}
			instance.saveChunksToStorage().thenRun {
				Minigames.send(sender, "Your current instance has been saved to disk.")
			}
		}
	}
}
