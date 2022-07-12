package dev.qixils.aviary.common;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Chat {
	// local (i.e. per-server) map of tracked channels
	private final @NotNull Map<UUID, Channel> registeredChannels = new HashMap<>();

}
