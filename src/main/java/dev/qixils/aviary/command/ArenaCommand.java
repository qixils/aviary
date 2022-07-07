package dev.qixils.aviary.command;

import net.minestom.server.command.builder.Command;

public class ArenaCommand extends Command {
	public ArenaCommand() {
		super("arena");
		addSubcommand(new ArenaCreateCommand());
	}
}
