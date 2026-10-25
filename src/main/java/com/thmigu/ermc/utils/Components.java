package com.thmigu.ermc.utils;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.object.ObjectContents;

import java.util.UUID;

public class Components {
	public final static Component JOIN_ICON = Component.object().contents(
			ObjectContents.sprite(
					Key.key("minecraft:gui"),
					Key.key("container/beacon/confirm")
			)
	).build().color(NamedTextColor.WHITE);
	public final static Component LEAVE_ICON = Component.object().contents(
			ObjectContents.sprite(
					Key.key("minecraft:gui"),
					Key.key("container/beacon/cancel")
			)
	).build().color(NamedTextColor.WHITE);

	public final static Component DEATH_ICON = Component.object().contents(
			ObjectContents.sprite(
					Key.key("minecraft:particles"),
					Key.key("trial_omen")
			)
	).build().color(NamedTextColor.YELLOW);

	public static Component playerHead(UUID uuid) {
		Component head = Component.object().contents(
				ObjectContents.playerHead(uuid)
		).build().color(NamedTextColor.WHITE);

		return head;
	}

	public static Component playerComponent(Player player) {
		String name = player.getUsername();
		UUID uuid = player.getUniqueId();

		Component head = playerHead(uuid);

		Component playerComponent = head
				.append(Component.text(".", NamedTextColor.BLACK))
				.append(Component.text(name, NamedTextColor.GOLD));

		return playerComponent;
	}

	public static Component playerComponent(String name, UUID uuid) {
		Component head = playerHead(uuid);

		Component playerComponent = head
				.append(Component.text(".", NamedTextColor.BLACK))
				.append(Component.text(name, NamedTextColor.GOLD));

		return playerComponent;
	}
}
