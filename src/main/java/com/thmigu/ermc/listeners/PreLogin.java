package com.thmigu.ermc.listeners;

import com.google.inject.Inject;
import com.thmigu.ermc.utils.Config;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.thmigu.ermc.utils.Components.NOT_WHITELISTED;
import static com.thmigu.ermc.utils.Components.SERVER_ERROR;

public class PreLogin {
	private final Config config;

	@Inject
	public PreLogin(Config config) {
		this.config = config;
	}

	@Subscribe
	public void onJoin(PreLoginEvent event) {
		try {
			UUID uuid = event.getUniqueId();
			assert uuid != null;
			String formattedUUID = uuid.toString().replace("-", "");

			String restIp = config.restIp();
			int restPort = config.restPort();

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://%s:%d/api/whitelist/%s".formatted(restIp, restPort, formattedUUID)))
					.GET()
					.build();

			HttpResponse<String> response = client.send(
					request,
					HttpResponse.BodyHandlers.ofString()
			);

			if (response.body().equals("0")) {
				event.setResult(PreLoginEvent.PreLoginComponentResult.denied(NOT_WHITELISTED));
			}
		} catch (Exception ignore) {
			event.setResult(PreLoginEvent.PreLoginComponentResult.denied(SERVER_ERROR));
		}
	}
}
