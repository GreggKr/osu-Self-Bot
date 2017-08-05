package me.sirgregg.osubot.util;

import static me.sirgregg.osubot.util.helpers.URLUtil.sanatize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.util.helpers.URLUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import me.sirgregg.osubot.util.objects.Play;
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

	public String parseApproved(String approved) {
		switch (approved) {
			case "4":
				return "Loved";
			case "3":
				return "Qualified";
			case "2":
				return "Approved";
			case "1":
				return "Ranked";
			case "0":
				return "Pending";
			case "-1":
				return "WIP";
			case "-2":
				return "Graveyard";
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

	public User getUser(int userId, String mode) {
		int parsedMode = parseMode(mode);
		if (parsedMode == -1) {
			return null;
		}

		String id = Integer.toString(userId);
		String url = baseUrl + "get_user?k=" + sanatize(key) + "&u=" + sanatize(id) + "&type=id&m=" + parsedMode;

		if (exists(getJson(url))) {
			Gson gson = new GsonBuilder().create();
			User[] users = gson.fromJson(URLUtil.readURL(url), User[].class);

			return users[0];
		} else {
			return null;
		}
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

	public Beatmap getXthBestPlay(String username, int x, String mode) {
		return getBestPlays(username, Integer.toString(x), mode)[x - 1];
	}

	// Yes, I understand using a String for an amount is dumb, well, the osu!API uses Strings, so for simplicities sake, so am I.
	public Beatmap[] getBestPlays(String username, String amount, String mode) {
		int parsedMode = parseMode(mode);
		if (parsedMode == -1) {
			return null;
		}

		String url = baseUrl + "get_user_best?k=" + sanatize(key) + "&u=" + sanatize(username) + "&type=string&m=" + parsedMode + "&limit=" + amount;

		if (exists(getJson(url))) {
			Gson gson = new GsonBuilder().create();
			Play[] plays =  gson.fromJson(URLUtil.readURL(url), Play[].class);

			Beatmap[] beatmaps = new Beatmap[Integer.parseInt(amount)];
			for (int i = 0; i < plays.length; i++) {
				beatmaps[i] = getBeatmap(plays[i].getBeatmapId(), mode);
			}
			return beatmaps;
		}
		return null;
	}

	public Play[] getRecentPlays(String username, String amount, String mode) {
		int parsedMode = parseMode(mode);
		if (parsedMode == -1) {
			return null;
		}

		String url = baseUrl + "get_user_recent?k=" + sanatize(key) + "&u=" + sanatize(username) + "&type=string&m=" + parsedMode + "^limit=" + amount;

		if (exists(getJson(url))) {
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(URLUtil.readURL(url), Play[].class);
		}
		return null;
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
