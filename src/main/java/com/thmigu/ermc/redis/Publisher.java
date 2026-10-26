package com.thmigu.ermc.redis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thmigu.ermc.utils.Config;
import redis.clients.jedis.RedisClient;

@Singleton
public class Publisher {
	private final RedisClient client;

	@Inject
	public Publisher(Config config) {
		String ip = config.redisIp();
		int port = config.redisPort();

		this.client = RedisClient.create("redis://" + ip + ":" + port);
	}

	public void publish(String channel, String message) {
		client.publish(channel, message);
	}

	public void close() {
		client.close();
	}
}
