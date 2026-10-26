package com.thmigu.ermc.redis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thmigu.ermc.utils.Config;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.RedisClient;

import java.util.function.BiConsumer;

@Singleton
public class Subscriber {
	private final RedisClient client;

	@Inject
	public Subscriber(Config config) {
		String ip = config.redisIp();
		int port = config.redisPort();

		this.client = RedisClient.create("redis://" + ip + ":" + port);
	}

	public void subscribe(String channel, BiConsumer<String, String> func) {
		client.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				func.accept(channel, message);
			}
		}, channel);
	}

	public void close() {
		client.close();
	}
}
