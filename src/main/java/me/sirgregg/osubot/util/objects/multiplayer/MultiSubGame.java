package me.sirgregg.osubot.util.objects.multiplayer;


import com.google.gson.annotations.SerializedName;

/*

See MultiGame for details of what this class holds

*/
public class MultiSubGame {
	private String mods;
	private MultiScore[] scores;

	@SerializedName("game_id")
	private String gameId;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("beatmap_id")
	private String beatmapId;

	@SerializedName("play_mode")
	private String playMode;

	@SerializedName("match_type")
	private String matchType;

	@SerializedName("scoring_type")
	private String scoringType;

	@SerializedName("team_type")
	private String teamType;

	public String getMods() {
		return mods;
	}

	public MultiScore[] getScores() {
		return scores;
	}

	public String getGameId() {
		return gameId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getBeatmapId() {
		return beatmapId;
	}

	public String getPlayMode() {
		return playMode;
	}

	public String getMatchType() {
		return matchType;
	}

	public String getScoringType() {
		return scoringType;
	}

	public String getTeamType() {
		return teamType;
	}
}
