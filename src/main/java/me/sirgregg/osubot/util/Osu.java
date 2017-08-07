package me.sirgregg.osubot.util;

import static me.sirgregg.osubot.util.helpers.URLUtil.sanatize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.util.file.FileDownloader;
import me.sirgregg.osubot.util.helpers.URLUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import me.sirgregg.osubot.util.objects.Play;
import me.sirgregg.osubot.util.objects.User;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Osu {
	private String baseUrl = "https://osu.ppy.sh/api/";
	private String key = OsuBot.getConfiguration().getOsuToken();

	public int parseMode(String mode) {
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

		String url = baseUrl + "get_user_recent?k=" + sanatize(key) + "&u=" + sanatize(username) + "&type=string&m=" + parsedMode + "&limit=" + amount;

		if (exists(getJson(url))) {
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(URLUtil.readURL(url), Play[].class);
		}
		return null;
	}

	/*

	@param mode should be *unparsed*

	@returns -1 if file is null
	@returns -2 if process fails
	@returns -3 if mode isn't correct
	@returns -4 if the return from oppai is null.
	@returns pp if everything works

	 */
	public String getPP(String beatmapId, String mode, String accuracy, String mods, String combo, String misses, String scoreVersion) {
		int parsedMode = parseMode(mode);
		if (parsedMode == -1) {
			return "-3";
		}
		String input = "https://osu.ppy.sh/osu/" + beatmapId + "?m=" + mode;

		File file = null;

		// Used to calculate pp
		Process osu;

		try {
			String result = FileDownloader.downloadFromUrl(new URL(input), "map.osu");
			file = new File(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Beatmap beatmap = getBeatmap(beatmapId, mode);

		if (accuracy == null) {
			accuracy = "100%";
		}

		if (mods == null) {
			mods = "+nomod";
		}

		if (combo == null) {
			combo = beatmap.getMaxCombo() + "x";
		}

		if (misses == null) {
			misses = "0m";
		}

		if (scoreVersion == null) {
			scoreVersion = "scorev1";
		}


		if (!accuracy.contains("%")) {
			accuracy = accuracy + "%";
		}

		if (!mods.contains("+")) {
			mods = "+" + mods;
		}

		if (!combo.contains("x")) {
			combo = combo + "x";
		}

		if (!misses.contains("m")) {
			misses = misses + "m";
		}

		System.out.println("Acc: " + accuracy);
		System.out.println("mods: " + mods);
		System.out.println("combo: " + combo);
		System.out.println("misess: " + misses);
		System.out.println("score v: " + scoreVersion);

		if (file != null) {
			try {
				osu = new ProcessBuilder("lib\\oppai.exe", file.getAbsolutePath(), accuracy, mods, combo, misses, scoreVersion).start();

				InputStream is = osu.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(is);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String read = null;
				String line;

				List<String> infoArray = new ArrayList<>();

				while((line = bufferedReader.readLine()) != null) {
					read = read + line + "\n"; // TODO: Use a builder
					infoArray.add(line + "\n");
				}

				if (read != null) {
					return infoArray.get(28); // PP
				} else{
					return "-4";
				}

			} catch (IOException e) {
				return "-2";
			}
		} else {
			return "-1";
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
