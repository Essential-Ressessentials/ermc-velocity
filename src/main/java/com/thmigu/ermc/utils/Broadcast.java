package com.thmigu.ermc.utils;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import net.kyori.adventure.text.Component;

public class Broadcast {
	private final ProxyServer server;

	@Inject
	public Broadcast(ProxyServer server) {
		this.server = server;
	}

	public void sendAll(Component message) {
		for (Player player : server.getAllPlayers()) {
			player.sendMessage(message);
		}
	}
}
