package dex.qixils.aviary.command;

import dex.qixils.aviary.Minigames;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;

/**
 * Saves the current instance to disk.
 */
public class SaveInstanceCommand extends Command {

	/**
	 * Constructs a new command.
	 */
	public SaveInstanceCommand() {
		super("save-instance");
		setDefaultExecutor((sender, context) -> {
			if (!(sender instanceof Entity entity)) {
				Minigames.alert(sender, "This command can only be executed by physical entities.");
				return;
			}
			Instance instance = entity.getInstance();
			if (instance == null) {
				Minigames.alert(sender, "You are not in an instance.");
				return;
			}
			if (instance.getTag(Minigames.DISABLE_SAVING_TAG)) {
				Minigames.alert(sender, "This instance cannot be saved to disk.");
				return;
			}
			instance.saveChunksToStorage().thenRun(() -> Minigames.send(sender, "Your current instance has been saved to disk."));
		});
	}
}
