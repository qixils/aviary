package dev.qixils.aviary.proxy

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

@Plugin(
	id = "aviary",
	name = "Aviary",
	version = "1.0.0-SNAPSHOT",
	url = "https://aviary.qixils.dev",
	description = "Aviary's proxy plugin",
	authors = ["qixils"]
)
class AviaryProxy @Inject constructor(
	private val server: ProxyServer,
	private val logger: Logger
) {

	@Subscribe
	fun onProxyInitialization(event: ProxyInitializeEvent?) {
		// Register event listeners
		//server.getEventManager().register(this, );
	}
}
