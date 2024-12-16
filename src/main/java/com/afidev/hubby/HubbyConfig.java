package com.afidev.hubby;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HubbyConfig {
    private static final String CONFIG_FILE = "config/hubby.json";
    private static ConfigData configData = new ConfigData();

    public static class Location {
        public Double x, y, z;
        public Float yaw, pitch;
        public String dimension = "minecraft:overworld";
    }

    public static class Messages {
        public String teleported_to_hub = "&aTeleported to hub.";
        public String teleported_to_lobby = "&aTeleported to lobby.";
        public String teleported_to_spawn = "&aTeleported to spawn.";
        public String hub_set_confirmation = "&aHub has been set.";
        public String lobby_set_confirmation = "&aLobby has been set.";
        public String spawn_set_confirmation = "&aSpawn has been set.";
        public String hub_not_set = "&cHub has not been set yet.";
        public String lobby_not_set = "&cLobby has not been set yet.";
        public String spawn_not_set = "&cSpawn has not been set yet.";
        public String no_permission = "&cYou do not have permission to use this command.";
        public String player_only = "&cThis command can only be used by a player.";
        public String cannot_find_dimension = "&cCannot find this dimension.";
    }

    public static class ConfigData {
        public Location hub = null;
        public Location lobby = null;
        public Location spawn = null;
        public Messages messages = new Messages();

        // Getters pour accéder aux messages et autres données
        public Messages getMessages() {
            return messages;
        }

        public Location getHub() {
            return hub;
        }

        public Location getLobby() {
            return lobby;
        }

        public Location getSpawn() {
            return spawn;
        }
    }

    // Ajout du getter pour configData
    public static ConfigData getConfigData() {
        return configData;
    }

    public static void setHub(Double x, Double y, Double z, Float yaw, Float pitch, String dimension) {
        if (x == null || y == null || z == null) {
            Hubby.LOGGER.error("Hub coordinates not defined correctly");
            return;
        }

        configData.hub = new Location();
        configData.hub.x = x;
        configData.hub.y = y;
        configData.hub.z = z;
        configData.hub.yaw = yaw;
        configData.hub.pitch = pitch;
        configData.hub.dimension = dimension != null ? dimension : "minecraft:overworld";
        save();
    }

    public static void setLobby(Double x, Double y, Double z, Float yaw, Float pitch, String dimension) {
        if (x == null || y == null || z == null) {
            Hubby.LOGGER.error("Lobby coordinates not defined correctly");
            return;
        }

        configData.lobby = new Location();
        configData.lobby.x = x;
        configData.lobby.y = y;
        configData.lobby.z = z;
        configData.lobby.yaw = yaw;
        configData.lobby.pitch = pitch;
        configData.lobby.dimension = dimension != null ? dimension : "minecraft:overworld";
        save();
    }

    public static void setSpawn(Double x, Double y, Double z, Float yaw, Float pitch, String dimension) {
        if (x == null || y == null || z == null) {
            Hubby.LOGGER.error("Spawn coordinates not defined correctly");
            return;
        }

        configData.spawn = new Location();
        configData.spawn.x = x;
        configData.spawn.y = y;
        configData.spawn.z = z;
        configData.spawn.yaw = yaw;
        configData.spawn.pitch = pitch;
        configData.spawn.dimension = dimension != null ? dimension : "minecraft:overworld";
        save();
    }

    public static boolean isHubSet() {
        return configData.hub != null && configData.hub.x != null && configData.hub.y != null && configData.hub.z != null;
    }

    public static boolean isLobbySet() {
        return configData.lobby != null && configData.lobby.x != null && configData.lobby.y != null && configData.lobby.z != null;
    }

    public static boolean isSpawnSet() {
        return configData.spawn != null && configData.spawn.x != null && configData.spawn.y != null && configData.spawn.z != null;
    }

    public static Location getHub() {
        return configData.hub;
    }

    public static Location getLobby() {
        return configData.lobby;
    }

    public static Location getSpawn() {
        return configData.spawn;
    }

    public static void load() {
        Path path = Paths.get(CONFIG_FILE);
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                Gson gson = new Gson();
                configData = gson.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                Hubby.LOGGER.error("Failed to load configuration", e);
            }
        } else {
            // Si le fichier JSON n'existe pas, créez-le avec les valeurs par défaut
            save();
        }

        // Vérification des valeurs des messages, sans conversion en \u0026
        if (configData.messages.teleported_to_hub == null) configData.messages.teleported_to_hub = "&aTeleported to hub.";
        if (configData.messages.teleported_to_lobby == null) configData.messages.teleported_to_lobby = "&aTeleported to lobby.";
        if (configData.messages.teleported_to_spawn == null) configData.messages.teleported_to_spawn = "&aTeleported to spawn.";
        if (configData.messages.hub_set_confirmation == null) configData.messages.hub_set_confirmation = "&aHub has been set.";
        if (configData.messages.lobby_set_confirmation == null) configData.messages.lobby_set_confirmation = "&aLobby has been set.";
        if (configData.messages.spawn_set_confirmation == null) configData.messages.spawn_set_confirmation = "&aSpawn has been set.";
        if (configData.messages.hub_not_set == null) configData.messages.hub_not_set = "&cHub has not been set yet.";
        if (configData.messages.lobby_not_set == null) configData.messages.lobby_not_set = "&cLobby has not been set yet.";
        if (configData.messages.spawn_not_set == null) configData.messages.spawn_not_set = "&cSpawn has not been set yet.";
        if (configData.messages.no_permission == null) configData.messages.no_permission = "&cYou do not have permission to use this command.";
        if (configData.messages.player_only == null) configData.messages.player_only = "&cThis command can only be used by a player.";
        if (configData.messages.cannot_find_dimension == null) configData.messages.cannot_find_dimension = "&cCannot find this dimension.";

        save(); // Sauvegarder après avoir mis les valeurs par défaut dans les messages
    }

    private static void save() {
        Path path = Paths.get(CONFIG_FILE);
        try {
            Files.createDirectories(path.getParent()); // Crée le répertoire si nécessaire
            try (Writer writer = Files.newBufferedWriter(path)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(configData, writer);
            }
        } catch (IOException e) {
            Hubby.LOGGER.error("Failed to save configuration", e);
        }
    }
}
