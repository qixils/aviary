package dev.qixils.aviary.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.type.AnvilInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Utility class to prompt a player to enter a value.
 */
public final class AnvilPrompt {

	// TODO idk if the "prompt" option will work like I hope it does

	/**
	 * Prompts a player to enter a value.
	 *
	 * @param player       the player to prompt
	 * @param prompt       the prompt to display
	 * @param defaultValue the default value to suggest to the player
	 * @param converter    the converter to use to convert the input to the desired return type
	 * @param <T> 	       the type of the return value
	 * @return a completable future containing the converted value
	 *         or null if the player cancels the prompt or their input is invalid
	 */
	public static <T> @NotNull CompletableFuture<@Nullable T> prompt(@NotNull Player player, @NotNull Component prompt, @Nullable String defaultValue, @NotNull Function<String, T> converter) {
		CompletableFuture<T> future = new CompletableFuture<>();
		AnvilInventory inventory = new AnvilInventory(prompt);
		ItemStack item = ItemStack.builder(Material.PAPER)
				.displayName(defaultValue == null ? Component.empty() : Component.text(defaultValue))
				.build();
		inventory.addItemStack(item);
		inventory.addInventoryCondition(($, slot, clickType, inventoryConditionResult) -> {
			if (clickType != ClickType.LEFT_CLICK || slot != 2) {
				inventoryConditionResult.setCancel(true);
				return;
			}
			Component inputComponent = Objects.requireNonNullElseGet(inventoryConditionResult.getClickedItem().getDisplayName(), Component::empty);
			String input = PlainTextComponentSerializer.plainText().serialize(inputComponent);
			try {
				future.complete(converter.apply(input));
			} catch (Exception e) {
				future.completeExceptionally(e);
			}
		});
		player.openInventory(inventory);
		return future;
	}

	/**
	 * Builds a new prompt.
	 *
	 * @return a new prompt builder
	 */
	public static <T> @NotNull Builder<T> builder() {
		return new Builder<>();
	}

	/**
	 * Builds a new prompt which will return a {@link String} value.
	 *
	 * @return a new prompt builder
	 */
	public static @NotNull Builder<String> stringBuilder() {
		return new Builder<String>().converter(Function.identity());
	}

	/**
	 * Builds a new prompt which will return an {@link Integer} value.
	 *
	 * @return a new prompt builder
	 */
	public static @NotNull Builder<Integer> integerBuilder() {
		return new Builder<Integer>().converter(Integer::parseInt);
	}

	/**
	 * Builds a new prompt which will return a {@link Double} value.
	 *
	 * @return a new prompt builder
	 */
	public static @NotNull Builder<Double> doubleBuilder() {
		return new Builder<Double>().converter(Double::parseDouble);
	}

	/**
	 * Builds a new prompt which will return a {@link Float} value.
	 *
	 * @return a new prompt builder
	 */
	public static @NotNull Builder<Float> floatBuilder() {
		return new Builder<Float>().converter(Float::parseFloat);
	}

	/**
	 * Builds a new prompt which will return a {@link Long} value.
	 *
	 * @return a new prompt builder
	 */
	public static @NotNull Builder<Long> longBuilder() {
		return new Builder<Long>().converter(Long::parseLong);
	}

	/**
	 * Builder for anvil prompts.
	 */
	public static final class Builder<T> {
		private @Nullable Player player;
		private @Nullable Component prompt;
		private @Nullable String defaultValue;
		private @Nullable Function<String, T> converter;

		/**
		 * Sets the player to prompt.
		 *
		 * @param player the player to prompt
		 * @return this builder
		 */
		public @NotNull Builder<T> player(@NotNull Player player) {
			this.player = player;
			return this;
		}

		/**
		 * Sets the prompt to display.
		 *
		 * @param prompt the prompt to display
		 * @return this builder
		 */
		public @NotNull Builder<T> prompt(@NotNull Component prompt) {
			this.prompt = prompt;
			return this;
		}

		/**
		 * Sets the default value to suggest to the player.
		 *
		 * @param defaultValue the default value to suggest to the player
		 * @return this builder
		 */
		public @NotNull Builder<T> defaultValue(@Nullable String defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		/**
		 * Sets the converter to use to convert the input to the desired return type.
		 *
		 * @param converter the converter to use to convert the input to the desired return type
		 * @return this builder
		 */
		public @NotNull Builder<T> converter(@NotNull Function<String, T> converter) {
			this.converter = converter;
			return this;
		}

		/**
		 * Prompts the player to enter a value.
		 *
		 * @return a completable future containing the converted value
		 *         or null if the player cancels the prompt or their input is invalid
		 */
		public @NotNull CompletableFuture<@Nullable T> build() {
			if (player == null)
				throw new IllegalStateException("Player cannot be null.");
			if (prompt == null)
				throw new IllegalStateException("Prompt cannot be null.");
			if (converter == null)
				throw new IllegalStateException("Converter cannot be null.");
			return AnvilPrompt.prompt(player, prompt, defaultValue, converter);
		}
	}
}
