package com.thmigu.ermc.listeners;

import com.google.inject.Inject;
import com.thmigu.ermc.redis.Publisher;
import com.thmigu.ermc.utils.Broadcast;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import static com.thmigu.ermc.utils.Components.playerComponent;
import static com.thmigu.ermc.utils.webhook.Chat.chatPayload;

public class Chat {
	private final Broadcast broadcast;
	private final Publisher publisher;

	@Inject
	public Chat(
			Broadcast broadcast,
			Publisher publisher
	) {
		this.broadcast = broadcast;
		this.publisher = publisher;
	}

	@Subscribe
	public void onChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String msg = event.getMessage();

		Component playerComponent = playerComponent(player);

		Component message = Component.empty()
				.append(playerComponent)
				.append(Component.text(" » ", NamedTextColor.DARK_AQUA))
				.append(Component.text(msg, NamedTextColor.AQUA));;

		broadcast.sendAll(message);

		String webhookPayload = chatPayload(player, msg);
		publisher.publish("ermc:event:chat", webhookPayload);
	}
}
