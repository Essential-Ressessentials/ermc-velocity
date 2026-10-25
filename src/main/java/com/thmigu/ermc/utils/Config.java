package com.thmigu.ermc.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Singleton
public final class Config {
    private final TomlParseResult toml;

    @Inject
    public Config(@DataDirectory Path dataDirectory) throws IOException {
        try {
            Files.createDirectories(dataDirectory);

            Path configPath = dataDirectory.resolve("config.toml");

            if (Files.notExists(configPath)) {
                try (InputStream in = getClass()
                        .getResourceAsStream("/config.toml")) {
                    if (in == null) {
                        throw new IOException(
                                "Missing default config.toml resource"
                        );
                    }
                    Files.copy(in, configPath);
                }
            }

            this.toml = Toml.parse(configPath);

            if (toml.hasErrors()) {
                throw new IllegalStateException(
                        "Invalid config.toml: " + toml.errors()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public String ip() {
        return toml.getString("redis.ip");
    }

    public int port() {
        return Objects.requireNonNull(toml.getLong("redis.port")).intValue();
    }
}