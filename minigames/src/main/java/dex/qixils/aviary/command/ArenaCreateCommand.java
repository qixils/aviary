package dex.qixils.aviary.command;

import dex.qixils.aviary.Minigames;
import dex.qixils.aviary.utils.AnvilPrompt;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

import java.util.concurrent.CompletableFuture;

public class ArenaCreateCommand extends Command {
	public ArenaCreateCommand() {
		super("create");
		setDefaultExecutor((sender, context) -> {
			if (!(sender instanceof Player player)) {
				Minigames.alert(sender, "This command can only be executed by players.");
				return;
			}
			CompletableFuture<String> nameFuture = AnvilPrompt.stringBuilder()
					.player(player)
					.prompt(Component.text("Please enter the ID of the arena to create"))
					.build();
			nameFuture.thenAccept(name -> {
				// TODO: Prompt for game type
				// TODO: Create arena
			});
		});
	}
}
