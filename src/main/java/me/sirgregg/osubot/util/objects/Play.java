package me.sirgregg.osubot.util.objects;

import com.google.gson.annotations.SerializedName;

public class Play {
	private String score, count50, count100, count300, perfect, date, rank, pp;

	@SerializedName("beatmap_id")
	private String beatmapId;

	@SerializedName("maxcombo")
	private String maxCombo;

	@SerializedName("countmiss")
	private String countMiss;

	@SerializedName("countkatu")
	private String countKatu;

	@SerializedName("countgeki")
	private String countGeki;

	@SerializedName("enabled_mods")
	private String enabledMods;

	@SerializedName("user_id")
	private String userId;

	public String getScore() {
		return score;
	}

	public String getCount50() {
		return count50;
	}

	public String getCount100() {
		return count100;
	}

	public String getCount300() {
		return count300;
	}

	public String getPerfect() {
		return perfect;
	}

	public String getDate() {
		return date;
	}

	public String getRank() {
		return rank;
	}

	public String getPp() {
		return pp;
	}

	public String getBeatmapId() {
		return beatmapId;
	}

	public String getMaxCombo() {
		return maxCombo;
	}

	public String getCountMiss() {
		return countMiss;
	}

	public String getCountKatu() {
		return countKatu;
	}

	public String getCountGeki() {
		return countGeki;
	}

	public String getEnabledMods() {
		return enabledMods;
	}

	public String getUserId() {
		return userId;
	}
}
