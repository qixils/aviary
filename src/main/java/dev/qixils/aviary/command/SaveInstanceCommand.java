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
			instance.saveChunksToStorage().thenRun(() -> Aviary.send(sender, "Saved current instance."));
			// TODO: create a static method in Aviary which clones an Instance and sets a tag marking it as a temporary instance.
			//  then, in this command, we can prevent temporary instances from being saved.
		});
	}
}
