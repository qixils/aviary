package dev.qixils.aviary;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.potion.Potion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Loadout implements Identifiable<UUID> {
	private final @NotNull UUID uuid;
	private final @NotNull ItemStack[] items;
	private @NotNull ItemStack offhand = ItemStack.AIR;
	private @NotNull ItemStack helmet = ItemStack.AIR;
	private @NotNull ItemStack chestplate = ItemStack.AIR;
	private @NotNull ItemStack leggings = ItemStack.AIR;
	private @NotNull ItemStack boots = ItemStack.AIR;
	private final @NotNull List<Potion> potions;

	/**
	 * Initializes a new empty loadout.
	 */
	public Loadout() {
		uuid = UUID.randomUUID();
		items = new ItemStack[9 * 4];
		potions = new ArrayList<>();
	}

	/**
	 * Initializes a new loadout with the given items and ID.
	 *
	 * @param uuid    the ID of the loadout
	 * @param items   the primary items of the loadout
	 * @param offhand the offhand item of the loadout
	 */
	// TODO BSON annotations
	public Loadout(@NotNull UUID uuid,
				   @NotNull ItemStack[] items,
				   @NotNull ItemStack offhand,
				   @NotNull ItemStack helmet,
				   @NotNull ItemStack chestplate,
				   @NotNull ItemStack leggings,
				   @NotNull ItemStack boots,
				   @NotNull List<Potion> potions) {
		this.uuid = uuid;
		this.items = items;
		this.offhand = offhand;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.potions = new ArrayList<>(potions);
	}

	/**
	 * Applies this loadout to the given player.
	 *
	 * @param player the player to apply the loadout to
	 */
	public void applyTo(@NotNull Player player) {
		// items
		for (int i = 0; i < items.length; i++) {
			ItemStack item = Objects.requireNonNullElse(items[i], ItemStack.AIR);
			player.getInventory().setItemStack(i, item);
		}
		player.setItemInOffHand(offhand);
		player.setHelmet(helmet);
		player.setChestplate(chestplate);
		player.setLeggings(leggings);
		player.setBoots(boots);

		// potions
		player.clearEffects();
		potions.forEach(player::addEffect);
	}

	// boilerplate

	@Override
	public @NotNull UUID getId() {
		return uuid;
	}

	/**
	 * Gets the items of this loadout.
	 * The returned array is not a copy and may be modified.
	 *
	 * @return the items of this loadout
	 */
	public @NotNull ItemStack[] getItems() {
		return items;
	}

	/**
	 * Gets the offhand item of this loadout.
	 *
	 * @return the offhand item of this loadout
	 */
	public @NotNull ItemStack getOffHand() {
		return offhand;
	}

	/**
	 * Sets the offhand item of this loadout.
	 *
	 * @param offhand the offhand item of this loadout
	 */
	public void setOffHand(@NotNull ItemStack offhand) {
		this.offhand = offhand;
	}

	/**
	 * Gets the helmet of this loadout.
	 *
	 * @return the helmet of this loadout
	 */
	public @NotNull ItemStack getHelmet() {
		return helmet;
	}

	/**
	 * Sets the helmet of this loadout.
	 *
	 * @param helmet the helmet of this loadout
	 */
	public void setHelmet(@NotNull ItemStack helmet) {
		this.helmet = helmet;
	}

	/**
	 * Gets the chestplate of this loadout.
	 *
	 * @return the chestplate of this loadout
	 */
	public @NotNull ItemStack getChestplate() {
		return chestplate;
	}

	/**
	 * Sets the chestplate of this loadout.
	 *
	 * @param chestplate the chestplate of this loadout
	 */
	public void setChestplate(@NotNull ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	/**
	 * Gets the leggings of this loadout.
	 *
	 * @return the leggings of this loadout
	 */
	public @NotNull ItemStack getLeggings() {
		return leggings;
	}

	/**
	 * Sets the leggings of this loadout.
	 *
	 * @param leggings the leggings of this loadout
	 */
	public void setLeggings(@NotNull ItemStack leggings) {
		this.leggings = leggings;
	}

	/**
	 * Gets the boots of this loadout.
	 *
	 * @return the boots of this loadout
	 */
	public @NotNull ItemStack getBoots() {
		return boots;
	}

	/**
	 * Sets the boots of this loadout.
	 *
	 * @param boots the boots of this loadout
	 */
	public void setBoots(@NotNull ItemStack boots) {
		this.boots = boots;
	}

	/**
	 * Gets the potions of this loadout.
	 * The returned list is not a copy and may be modified.
	 *
	 * @return the potions of this loadout
	 */
	public @NotNull List<Potion> getPotions() {
		return potions;
	}

	public static @NotNull Loadout load(@NotNull UUID uuid) {
		// TODO load from database
	}
}
