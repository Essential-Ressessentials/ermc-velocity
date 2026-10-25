package com.thmigu.ermc.utils.webhook;

import com.google.gson.Gson;
import com.velocitypowered.api.proxy.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JoinLeave {
	public static String jlPayload(Player player) {
		String name = player.getUsername();
		UUID uuid = player.getUniqueId();

		Map<String, String> payload = new HashMap<>();

		payload.put("ign", name);
		payload.put("uuid", uuid.toString());

		Gson gson = new Gson();
		return gson.toJson(payload);
	}
}
