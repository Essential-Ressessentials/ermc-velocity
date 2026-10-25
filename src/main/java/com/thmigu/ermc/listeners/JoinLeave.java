package com.thmigu.ermc.listeners;

import com.google.inject.Inject;
import com.thmigu.ermc.redis.Publisher;
import com.thmigu.ermc.utils.Broadcast;
import com.thmigu.ermc.utils.Components;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import static com.thmigu.ermc.utils.Components.playerComponent;
import static com.thmigu.ermc.utils.webhook.JoinLeave.jlPayload;

public class JoinLeave {
	private final Broadcast broadcast;
	private final Publisher publisher;

	@Inject
	public JoinLeave(
		Broadcast broadcast,
		Publisher publisher
	) {
		this.broadcast = broadcast;
		this.publisher = publisher;
	}

	@Subscribe
	public void onJoin(LoginEvent event) {
		Player player = event.getPlayer();

		Component playerComponent = playerComponent(player);

		Component message = Component.empty()
			.append(Component.text("[", NamedTextColor.DARK_AQUA))
			.append(Components.JOIN_ICON)
			.append(Component.text("] ", NamedTextColor.DARK_AQUA))
			.append(playerComponent);

		broadcast.sendAll(message);

		String webhookPayload = jlPayload(player);
		publisher.publish("ermc:event:join", webhookPayload);
	}

	@Subscribe
	public void onLeave(DisconnectEvent event) {
		Player player = event.getPlayer();

		Component playerComponent = playerComponent(player);

		Component message = Component.empty()
				.append(Component.text("[", NamedTextColor.DARK_AQUA))
				.append(Components.LEAVE_ICON)
				.append(Component.text("] ", NamedTextColor.DARK_AQUA))
				.append(playerComponent);

		broadcast.sendAll(message);

		String webhookPayload = jlPayload(player);
		publisher.publish("ermc:event:leave", webhookPayload);
	}
}
