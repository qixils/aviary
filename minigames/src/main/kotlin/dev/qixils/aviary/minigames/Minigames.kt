package dev.qixils.aviary.minigames

import dev.qixils.aviary.minigames.utils.WorldUtils
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.spigotmc.event.player.PlayerSpawnLocationEvent

/**
 * The main class of the Aviary server.
 */
object Minigames : JavaPlugin(), Listener {

	/**
	 * The server's namespace.
	 */
	const val NAMESPACE = "aviary"

	/**
	 * Creates a key for the server.
	 */
	fun key(value: String): NamespacedKey = NamespacedKey(NAMESPACE, value)

	/**
	 * The server's lobby instance.
	 */
	val lobby: World by lazy { Bukkit.getWorld("lobby")!! }

	/**
	 * The spawn location.
	 */
	val spawnLocation: Location by lazy { Location(lobby, 0.0, 40.0, 0.0, 0f, 0f) }

	override fun onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this)
		// TODO: commands | TODO: reflection?
//		val commandManager = MinecraftServer.getCommandManager()
//		commandManager.register(ArenaCommand())
//		commandManager.register(SaveInstanceCommand())
	}

	override fun onDisable() {
		runBlocking {
			WorldUtils.closeWorlds()
		}
	}

	@EventHandler
	fun onLogin(event: PlayerSpawnLocationEvent) {
		event.spawnLocation = spawnLocation
	}
}
