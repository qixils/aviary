package dev.qixils.aviary.minigames.utils

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor

object ComponentUtils {
	/**
	 * The prefix used in responses to commands.
	 */
	val PREFIX: Component = Component.text().color(TextColor.color(0xFFFCB6))
		.append(Component.text("[!]", TextColor.color(0xEEBA66)))
		.append(Component.space())
		.build()

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
}
