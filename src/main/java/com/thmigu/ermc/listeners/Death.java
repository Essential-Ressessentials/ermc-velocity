package com.thmigu.ermc.listeners;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.thmigu.ermc.utils.Broadcast;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

import static com.thmigu.ermc.utils.Components.DEATH_ICON;
import static com.thmigu.ermc.utils.Components.playerComponent;

public class Death {
	private final Broadcast broadcast;

	@Inject
	public Death(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	private static class DeathMessage {
		String ign;
		UUID uuid;
		String msg;
	}

	public void onDeath(String ignore, String payload) {
		Gson gson = new Gson();

		DeathMessage deathMessage = gson.fromJson(payload, DeathMessage.class);

		Component message = Component.empty()
				.append(Component.text("[", NamedTextColor.DARK_AQUA))
				.append(DEATH_ICON)
				.append(Component.text("] ", NamedTextColor.DARK_AQUA))
				.append(formatDeath(deathMessage));

		broadcast.sendAll(message);
	}

	private static Component formatDeath(DeathMessage deathMessage) {
		Component formatted = Component.empty();

		for (String word : deathMessage.msg.split(" ")) {
			if (word.equals(deathMessage.ign)) {
				formatted = formatted.append(playerComponent(deathMessage.ign, deathMessage.uuid)).appendSpace();
				continue;
			}

			formatted = formatted.append(Component.text(word, NamedTextColor.AQUA)).appendSpace();
		}

		return formatted;
	}
}
