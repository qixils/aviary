package dev.qixils.aviary.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Plugin(id = "aviary",
		name = "Aviary",
		version = "1.0.0-SNAPSHOT",
		url = "https://aviary.qixils.dev",
		description = "Aviary's proxy plugin",
		authors = {"qixils"})
public class AviaryProxy {

	private final @NotNull ProxyServer server;
	private final @NotNull Logger logger;

	@Inject
	public AviaryProxy(@NotNull ProxyServer server, @NotNull Logger logger) {
		this.server = server;
		this.logger = logger;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		// Register event listeners
		//server.getEventManager().register(this, );
	}
}