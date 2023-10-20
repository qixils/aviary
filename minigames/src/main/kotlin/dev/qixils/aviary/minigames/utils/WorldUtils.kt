package dev.qixils.aviary.minigames.utils

import dev.qixils.aviary.minigames.Minigames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.WorldCreator
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.io.path.isDirectory

object WorldUtils {
	val worldContainer: Path by lazy { Bukkit.getWorldContainer().toPath() }
	private val clonedWorlds = mutableSetOf<NamespacedKey>()
	private val blacklistedPaths = listOf("uid.dat", "session.lock", "playerdata/.+", "advancements/.+", "stats/.+").map { it.toPattern() }

	fun validateNewWorld(name: String): Boolean {
		return !File(Bukkit.getWorldContainer(), name).exists()
	}


	suspend fun cloneWorld(world: World): World {
		val worldPath = world.worldFolder.toPath()
		val targetId = UUID.randomUUID()
		val targetName = Minigames.key("temp-${targetId}")
		clonedWorlds.add(targetName)
		val targetPath = File(Bukkit.getWorldContainer(), targetName.toString()).toPath()

		@Suppress("BlockingMethodInNonBlockingContext")
		withContext(Dispatchers.IO) {
			Files.walk(world.worldFolder.toPath())
				.forEach { path ->
					if (path.isDirectory())
						return@forEach
					val relative = worldPath.relativize(path)
					if (blacklistedPaths.any { it.matcher(relative.toString()).matches() })
						return@forEach
					val target = targetPath.resolve(relative)
					Files.createDirectories(target.parent)
					Files.copy(path, target)
				}
		}

		return WorldCreator(targetName).copy(world).createWorld()!!
	}

	suspend fun closeWorld(worldKey: NamespacedKey, save: Boolean) {
		clonedWorlds.remove(worldKey)
		var worldPath = worldContainer.resolve(worldKey.value())

		Bukkit.getWorld(worldKey)?.let { world ->
			worldPath = world.worldFolder.toPath()
			// teleport away
			val futures = world.players.map { it.teleportAsync(Minigames.spawnLocation) }
			CompletableFuture.allOf(*futures.toTypedArray()).await()
			// unload
			Bukkit.unloadWorld(world, save)
		}

		// delete
		if (!save) {
			withContext(Dispatchers.IO) {
				Files.walk(worldPath).forEach(Files::delete)
			}
		}
	}

	suspend fun closeWorlds() {
		clonedWorlds.forEach { closeWorld(it, false) }
	}
}
