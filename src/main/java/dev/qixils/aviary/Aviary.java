package dev.qixils.aviary;

import dev.qixils.aviary.command.SaveInstanceCommand;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * The main class of the Aviary server.
 */
public final class Aviary {
	/**
	 * The tag used to disable saving instances to disk.
	 */
	public static final Tag<Boolean> DISABLE_SAVING_TAG = Tag.Boolean("IsSavingDisabled").defaultValue(false);
	/**
	 * The prefix used in responses to commands.
	 */
	public static final Component PREFIX = Component.text().color(TextColor.color(0xFFFCB6))
			.append(Component.text('[', TextColor.color(0x777777), TextDecoration.BOLD))
			.append(Component.text("Aviary", TextColor.color(0xEEBA66)))
			.append(Component.text(']', TextColor.color(0x777777), TextDecoration.BOLD))
			.append(Component.text(' '))
			.build();
	private static InstanceManager instanceManager;
	private static InstanceContainer lobby;

	public static void main(String[] args) {
		// initialize server
		MinecraftServer server = MinecraftServer.init();
		instanceManager = MinecraftServer.getInstanceManager();

		// create lobby instance
		lobby = instanceManager.createInstanceContainer(new AnvilLoader("lobby")); // no generator is necessary; by default new chunks are filled with air

		// register events
		GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
		eventHandler.addListener(PlayerLoginEvent.class, event -> {
			final Player player = event.getPlayer();
			event.setSpawningInstance(lobby);
			player.setRespawnPoint(new Pos(0, 40, 0, 0, 0)); // TODO lobby pos
			// TODO set permission level?
		});

		// register commands
		CommandManager commandManager = MinecraftServer.getCommandManager();
		commandManager.register(new SaveInstanceCommand());

		// start server
		server.start("0.0.0.0", 25565);
	}

	/**
	 * Gets the server's instance manager.
	 *
	 * @return instance manager
	 */
	public static @NotNull InstanceManager getInstanceManager() {
		return instanceManager;
	}

	/**
	 * Gets the server's lobby instance.
	 *
	 * @return lobby instance
	 */
	public static @NotNull InstanceContainer getLobby() {
		return lobby;
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
	public static void send(@NotNull Audience audience, @NotNull Component message) {
		audience.sendMessage(PREFIX.append(message));
	}

	/**
	 * Formats and sends a message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	public static void send(@NotNull Audience audience, @NotNull String message) {
		send(audience, Component.text(message));
	}

	/**
	 * Formats and sends an alert message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	public static void alert(@NotNull Audience audience, @NotNull Component message) {
		send(audience, message.colorIfAbsent(NamedTextColor.RED));
	}

	/**
	 * Formats and sends an alert message to an audience.
	 *
	 * @param audience the audience to message
	 * @param message  the message to send
	 */
	public static void alert(@NotNull Audience audience, @NotNull String message) {
		alert(audience, Component.text(message));
	}
}
