package dex.qixils.aviary.minigames

import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.potion.Potion

/**
 * The kit of items and effects given to players of a game type.
 *
 * There is generally only one kit used in each game type,
 * though some complex games may have multiple kits.
 */
class Kit(
	/**
	 * The primary inventory items.
	 *
	 * Indices range from 0-35 and span the primary inventory slots
	 * from left-to-right, top-to-bottom. <!-- TODO: at least, I think! -->
	 */
	val items: Array<ItemStack> = Array(36) { ItemStack.AIR },
	/**
	 * The off-hand item.
	 */
	var offHand: ItemStack = ItemStack.AIR,
	/**
	 * The helmet item.
	 */
	var helmet: ItemStack = ItemStack.AIR,
	/**
	 * The chestplate item.
	 */
	var chestplate: ItemStack = ItemStack.AIR,
	/**
	 * The leggings item.
	 */
	var leggings: ItemStack = ItemStack.AIR,
	/**
	 * The boots item.
	 */
	var boots: ItemStack = ItemStack.AIR,
	/**
	 * The potion effects.
	 */
	val potions: List<Potion> = listOf(),
) {

	init {
		require(items.size == 36) { "Array of kit items must be 36 items long." }
	}

	/**
	 * Applies this kit to the given player.
	 *
	 * This removes any items or effects the player may already have
	 * and replaces them with the ones in this kit.
	 *
	 * @param player the player to apply the kit to
	 */
	fun applyTo(player: Player) {
		// items
		items.indices.forEach { i -> player.inventory.setItemStack(i, items[i]) }
		player.itemInOffHand = offHand
		player.helmet = helmet
		player.chestplate = chestplate
		player.leggings = leggings
		player.boots = boots

		// potions
		player.clearEffects()
		potions.forEach { potion -> player.addEffect(potion) }
	}
}
