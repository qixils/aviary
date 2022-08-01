package dex.qixils.aviary.minigames.command

import net.minestom.server.command.builder.Command

class ArenaCommand : Command("arena") {
    init {
        addSubcommand(ArenaCreateCommand())
    }
}