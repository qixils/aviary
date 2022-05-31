package dev.qixils.aviary.command;

import dev.qixils.aviary.Aviary;
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
				Aviary.alert(sender, "This command can only be executed by physical entities.");
				return;
			}
			Instance instance = entity.getInstance();
			if (instance == null) {
				Aviary.alert(sender, "You are not in an instance.");
				return;
			}
			if (instance.getTag(Aviary.DISABLE_SAVING_TAG)) {
				Aviary.alert(sender, "This instance cannot be saved to disk.");
				return;
			}
			instance.saveChunksToStorage().thenRun(() -> Aviary.send(sender, "Your current instance has been saved to disk."));
		});
	}
}
