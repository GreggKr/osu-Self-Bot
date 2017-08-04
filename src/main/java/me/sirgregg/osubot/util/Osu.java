package me.sirgregg.osubot.util;

import static me.sirgregg.osubot.util.helpers.URLUtil.sanatize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.util.helpers.URLUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import me.sirgregg.osubot.util.objects.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Osu {
	private String baseUrl = "https://osu.ppy.sh/api/";
	private String key = OsuBot.getConfiguration().getOsuToken();

	private int parseMode(String mode) {
		switch (mode.toLowerCase()) {
			case "std":
			case "standard":
				return 0;

			case "tak":
			case "taiko":
				return 1;

			case "ctb":
			case "catch":
				return 2;

			case "man":
			case "mania":
				return 3;
			default:
				return -1;
		}
	}

	public String formatMode(String mode) {
		switch (mode.toLowerCase()) {
			case "std":
			case "standard":
				return "osu!standard";

			case "tak":
			case "taiko":
				return "Taiko";

			case "ctb":
			case "catch":
				return "CtB";

			case "man":
			case "mania":
				return "Mania";
			default:
				return null;
		}
	}

	private String getJson(String url) {
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.connect();

			StringBuilder jsonBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonBuilder.append(line);
			}
			return jsonBuilder.toString();
		} catch (IOException e) {
			return null;
		}
	}

	private boolean exists(String json) {
		return !(new JsonParser().parse(json).getAsJsonArray().get(0) == null);
	}

	public User getUser(String username, String mode) {
		int parsedMode = parseMode(mode);
		if (parsedMode == -1) {
			return null;
		}

		String url = baseUrl + "get_user?k=" + sanatize(key) + "&u=" + sanatize(username) + "&type=string&m=" + parsedMode;

		if (exists(getJson(url))) {
			Gson gson = new GsonBuilder().create();
			User[] users = gson.fromJson(URLUtil.readURL(url), User[].class);

			return users[0];
		} else {
			return null;
		}
	}

	public Beatmap getBeatmap(String beatmapId, String mode) {
		int parsedMode = parseMode(mode);
		if (parsedMode == -1) {
			return null;
		}

		String url = baseUrl + "get_beatmaps?k=" + sanatize(key) + "&b=" + sanatize(beatmapId) + "&m=" + parsedMode;

		if (exists(getJson(url))) {
			Gson gson = new GsonBuilder().create();
			Beatmap[] beatmaps = gson.fromJson(URLUtil.readURL(url), Beatmap[].class);

			return beatmaps[0];
		} else {
			return null;
		}
	}

}
