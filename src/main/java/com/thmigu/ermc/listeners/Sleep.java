package com.thmigu.ermc.listeners;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.thmigu.ermc.redis.Publisher;
import com.thmigu.ermc.utils.Broadcast;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

import static com.thmigu.ermc.utils.Components.playerComponent;

public class Sleep {
	private final Broadcast broadcast;

	@Inject
	public Sleep(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	private static class SleepMessage {
		String ign;
		UUID uuid;
	}

	public void onSleep(String ignore, String payload) {
		Gson gson = new Gson();

		SleepMessage sleepMessage = gson.fromJson(payload, SleepMessage.class);

		Component playerComponent = playerComponent(sleepMessage.ign, sleepMessage.uuid);

		Component message = Component.empty()
			.append(Component.text("[", NamedTextColor.DARK_AQUA))
			.append(Component.text("🌙", NamedTextColor.DARK_GREEN))
			.append(Component.text("] ", NamedTextColor.DARK_AQUA))
			.append(playerComponent)
			.append(Component.text(" is sleeping", NamedTextColor.AQUA));

		broadcast.sendAll(message);
	}
}
