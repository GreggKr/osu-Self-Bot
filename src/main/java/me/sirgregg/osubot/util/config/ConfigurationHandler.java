package me.sirgregg.osubot.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;

public class ConfigurationHandler {
    private Configuration configuration;

    public void setupJson() {
        String path = "config.json";
        File file = new File(path);
        if (file.exists() && file.isFile()) return;

        try {
            Writer writer = new FileWriter(path);
            JsonObject object = new JsonObject();

            object.addProperty("discord_token", "Change this to your Discord token.");
            object.addProperty("osu_token", "Change this to your osu! token.");
            object.addProperty("color", "ff00c3");
            object.addProperty("lead", "osu!");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(object, writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseJson() {
        String path = "config.json";
        try {
            Reader reader = new FileReader(path);
            Gson gson = new GsonBuilder().create();

            configuration = gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
