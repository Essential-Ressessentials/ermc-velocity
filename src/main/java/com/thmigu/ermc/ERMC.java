package com.thmigu.ermc;

import com.google.inject.Inject;
import com.thmigu.ermc.listeners.*;
import com.thmigu.ermc.redis.Subscriber;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.ProxyServer;


public class ERMC {
	private final ProxyServer server;
	private final Subscriber subscriber;

	private final PreLogin preLogin;
	private final JoinLeave joinLeave;
	private final Chat chat;

	private final Sleep sleep;
	private final Death death;
	private final Discord discord;

	@Inject
	public ERMC(
		ProxyServer server,
		Subscriber subscriber,
		PreLogin preLogin,
		JoinLeave joinLeave,
		Chat chat,
		Sleep sleep,
		Death death,
		Discord discord
	) {
		this.server = server;
		this.subscriber = subscriber;

		this.preLogin = preLogin;
		this.joinLeave = joinLeave;
		this.chat = chat;

		this.sleep = sleep;
		this.death = death;
		this.discord = discord;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		registerEvents();
		registerSubscribers();
	}

	private void registerEvents() {
		EventManager eventManager = server.getEventManager();

		eventManager.register(this, preLogin);
		eventManager.register(this, joinLeave);
		eventManager.register(this, chat);
	}

	private void registerSubscribers() {
		new Thread(
				() -> subscriber.subscribe("ermc:event:sleep", sleep::onSleep),
				"sleep-sub"
		).start();
		new Thread(
				() -> subscriber.subscribe("ermc:event:death", death::onDeath),
				"death-sub"
		).start();
		new Thread(
				() -> subscriber.subscribe("ermc:discord", discord::onDiscord),
				"discord-sub"
		).start();
	}
}
