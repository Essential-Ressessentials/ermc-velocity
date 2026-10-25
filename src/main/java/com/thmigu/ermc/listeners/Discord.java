package com.thmigu.ermc.listeners;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.thmigu.ermc.utils.Broadcast;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Discord {
	private final Broadcast broadcast;

	@Inject
	public Discord(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	private static class DiscordMessage {
		String name;
		String msg;
	}

	public void onDiscord(String channel, String payload) {
		Gson gson = new Gson();

		DiscordMessage discordMessage = gson.fromJson(payload, DiscordMessage.class);

		Component message = Component.empty()
				.append(Component.text(discordMessage.name, NamedTextColor.BLUE))
				.append(Component.text(" » ", NamedTextColor.DARK_AQUA))
				.append(Component.text(discordMessage.msg, NamedTextColor.AQUA));;

		broadcast.sendAll(message);
	}
}
