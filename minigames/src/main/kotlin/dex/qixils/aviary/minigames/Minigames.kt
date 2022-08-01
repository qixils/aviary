package dex.qixils.aviary.minigames

import dex.qixils.aviary.minigames.command.ArenaCommand
import dex.qixils.aviary.minigames.command.SaveInstanceCommand
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.AnvilLoader
import net.minestom.server.tag.Tag

/**
 * The main class of the Aviary server.
 */
object Minigames {
	/**
	 * The tag used to disable saving instances to disk.
	 */
	val DISABLE_SAVING_TAG = Tag.Boolean("IsSavingDisabled").defaultValue(false)

	/**
	 * The prefix used in responses to commands.
	 */
	val PREFIX: Component = Component.text().color(TextColor.color(0xFFFCB6))
		.append(Component.text('[', TextColor.color(0x777777), TextDecoration.BOLD))
		.append(Component.text("Aviary", TextColor.color(0xEEBA66)))
		.append(Component.text(']', TextColor.color(0x777777), TextDecoration.BOLD))
		.append(Component.text(' '))
		.build()

	private val server = MinecraftServer.init()

	/**
	 * The server's instance manager.
	 */
	val instanceManager = MinecraftServer.getInstanceManager()

	/**
	 * The server's lobby instance.
	 */
	val lobby = instanceManager.createInstanceContainer(AnvilLoader("lobby"))

	init {
		// register events
		val eventHandler = MinecraftServer.getGlobalEventHandler()
		eventHandler.addListener(PlayerLoginEvent::class.java) { event ->
			val player = event.player
			event.setSpawningInstance(lobby)
			player.respawnPoint = Pos(0.0, 40.0, 0.0, 0f, 0f) // TODO lobby pos
		}

		// register commands | TODO: reflection?
		val commandManager = MinecraftServer.getCommandManager()
		commandManager.register(ArenaCommand())
		commandManager.register(SaveInstanceCommand())
	}

	/////////////////////////////
	//// Component Utilities ////
	/////////////////////////////

	/**
	 * Formats and sends a message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	fun send(audience: Audience, message: Component) {
		audience.sendMessage(PREFIX.append(message))
	}

	/**
	 * Formats and sends a message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	fun send(audience: Audience, message: String) {
		send(audience, Component.text(message))
	}

	/**
	 * Formats and sends an alert message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	fun alert(audience: Audience, message: Component) {
		send(audience, message.colorIfAbsent(NamedTextColor.RED))
	}

	/**
	 * Formats and sends an alert message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	fun alert(audience: Audience, message: String) {
		alert(audience, Component.text(message))
	}

	@JvmStatic
	fun main(args: Array<String>) {
		// start server
		server.start("0.0.0.0", 25565)
	}
}
