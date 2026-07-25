package net.loothelper.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("LootHelper");

    public static void init() {
        File dir = CONFIG_DIR.toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static boolean saveConfig(String name) {
        init();
        File configFile = CONFIG_DIR.resolve(name + ".json").toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject json = new JsonObject();
            json.addProperty("inventoryMove", true);
            json.addProperty("botToken", "");
            json.addProperty("chatId", "");
            
            GSON.toJson(json, writer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadConfig(String name) {
        File configFile = CONFIG_DIR.resolve(name + ".json").toFile();
        if (!configFile.exists()) {
            return false;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            return json != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeConfig(String name) {
        File configFile = CONFIG_DIR.resolve(name + ".json").toFile();
        if (configFile.exists()) {
            return configFile.delete();
        }
        return false;
    }
              }
