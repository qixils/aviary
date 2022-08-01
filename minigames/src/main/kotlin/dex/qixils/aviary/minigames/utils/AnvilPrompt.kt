package dex.qixils.aviary.minigames.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.minestom.server.entity.Player
import net.minestom.server.inventory.click.ClickType
import net.minestom.server.inventory.condition.InventoryConditionResult
import net.minestom.server.inventory.type.AnvilInventory
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Function

/**
 * Utility class to prompt a player to enter a value.
 */
object AnvilPrompt {

	// TODO idk if the "prompt" option will work like I hope it does

	/**
	 * Prompts a player to enter a value.
	 *
	 * @param player       the player to prompt
	 * @param prompt       the prompt to display
	 * @param defaultValue the default value to suggest to the player
	 * @param converter    the converter to use to convert the input to the desired return type
	 * @param T            the type of the return value
	 * @return a completable future containing the converted value
	 * or null if the player cancels the prompt or their input is invalid
	 */
	fun <T> prompt(
		player: Player,
		prompt: Component,
		defaultValue: String?,
		converter: Function<String, T>
	): CompletableFuture<T> {
		val future = CompletableFuture<T>()
		val inventory = AnvilInventory(prompt)
		val item = ItemStack.builder(Material.PAPER)
			.displayName(
				if (defaultValue == null)
					Component.empty()
				else
					Component.text(defaultValue)
			)
			.build()
		inventory.addItemStack(item)
		inventory.addInventoryCondition { _: Player?, slot: Int, clickType: ClickType, inventoryConditionResult: InventoryConditionResult ->
			if (clickType != ClickType.LEFT_CLICK || slot != 2) {
				inventoryConditionResult.isCancel = true
				return@addInventoryCondition
			}
			val inputComponent =
				Objects.requireNonNullElseGet(inventoryConditionResult.clickedItem.displayName) { Component.empty() }
			val input = PlainTextComponentSerializer.plainText().serialize(inputComponent!!)
			try {
				future.complete(converter.apply(input))
			} catch (e: Exception) {
				future.completeExceptionally(e)
			}
		}
		player.openInventory(inventory)
		return future
	}

	/**
	 * Builds a new prompt.
	 *
	 * @return a new prompt builder
	 */
	@Deprecated("Use Kotlin varargs instead")
	fun <T> builder(): Builder<T> {
		return Builder()
	}

	/**
	 * Builds a new prompt which will return a [String] value.
	 *
	 * @return a new prompt builder
	 */
	@Deprecated("Use Kotlin varargs instead")
	fun stringBuilder(): Builder<String> {
		return Builder<String>().converter(Function.identity())
	}

	/**
	 * Builds a new prompt which will return an [Integer] value.
	 *
	 * @return a new prompt builder
	 */
	@Deprecated("Use Kotlin varargs instead")
	fun integerBuilder(): Builder<Int> {
		return Builder<Int>().converter(Function { s: String -> s.toInt() })
	}

	/**
	 * Builds a new prompt which will return a [Double] value.
	 *
	 * @return a new prompt builder
	 */
	@Deprecated("Use Kotlin varargs instead")
	fun doubleBuilder(): Builder<Double> {
		return Builder<Double>().converter(Function { s: String -> s.toDouble() })
	}

	/**
	 * Builds a new prompt which will return a [Float] value.
	 *
	 * @return a new prompt builder
	 */
	@Deprecated("Use Kotlin varargs instead")
	fun floatBuilder(): Builder<Float> {
		return Builder<Float>().converter(Function { s: String -> s.toFloat() })
	}

	/**
	 * Builds a new prompt which will return a [Long] value.
	 *
	 * @return a new prompt builder
	 */
	@Deprecated("Use Kotlin varargs instead")
	fun longBuilder(): Builder<Long> {
		return Builder<Long>().converter(Function { s: String -> s.toLong() })
	}

	/**
	 * Builder for anvil prompts.
	 */
	@Deprecated("Use Kotlin varargs instead")
	class Builder<T> {
		private var player: Player? = null
		private var prompt: Component? = null
		private var defaultValue: String? = null
		private var converter: Function<String, T>? = null

		/**
		 * Sets the player to prompt.
		 *
		 * @param player the player to prompt
		 * @return this builder
		 */
		@Deprecated("Use Kotlin varargs instead")
		fun player(player: Player): Builder<T> {
			this.player = player
			return this
		}

		/**
		 * Sets the prompt to display.
		 *
		 * @param prompt the prompt to display
		 * @return this builder
		 */
		@Deprecated("Use Kotlin varargs instead")
		fun prompt(prompt: Component): Builder<T> {
			this.prompt = prompt
			return this
		}

		/**
		 * Sets the default value to suggest to the player.
		 *
		 * @param defaultValue the default value to suggest to the player
		 * @return this builder
		 */
		@Deprecated("Use Kotlin varargs instead")
		fun defaultValue(defaultValue: String): Builder<T> {
			this.defaultValue = defaultValue
			return this
		}

		/**
		 * Sets the converter to use to convert the input to the desired return type.
		 *
		 * @param converter the converter to use to convert the input to the desired return type
		 * @return this builder
		 */
		@Deprecated("Use Kotlin varargs instead")
		fun converter(converter: Function<String, T>): Builder<T> {
			this.converter = converter
			return this
		}

		/**
		 * Prompts the player to enter a value.
		 *
		 * @return a completable future containing the converted value
		 * or null if the player cancels the prompt or their input is invalid
		 */
		@Deprecated("Use Kotlin varargs instead")
		fun build(): CompletableFuture<T> {
			checkNotNull(player) { "Player cannot be null." }
			checkNotNull(prompt) { "Prompt cannot be null." }
			checkNotNull(converter) { "Converter cannot be null." }
			return prompt(player!!, prompt!!, defaultValue, converter!!)
		}
	}
}
