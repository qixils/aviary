package dev.qixils.aviary.common;

import dev.qixils.aviary.db.Identifiable;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.pointer.Pointered;

import java.util.UUID;

public interface Channel extends Identifiable<UUID>, Audience {
	// TODO: getter which determines what server handles the chat message (i.e. proxy vs non-proxy)
}
