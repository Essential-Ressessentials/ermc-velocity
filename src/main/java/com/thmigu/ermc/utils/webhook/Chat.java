package com.thmigu.ermc.utils.webhook;

import com.google.gson.Gson;
import com.velocitypowered.api.proxy.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Chat {
	public static String chatPayload(Player player, String message) {
		String name = player.getUsername();
		UUID uuid = player.getUniqueId();

		Map<String, String> payload = new HashMap<>();

		payload.put("ign", name);
		payload.put("uuid", uuid.toString());
		payload.put("msg", message);

		Gson gson = new Gson();
		return gson.toJson(payload);
	}
}
