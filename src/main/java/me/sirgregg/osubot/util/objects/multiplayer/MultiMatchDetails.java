package me.sirgregg.osubot.util.objects.multiplayer;

import com.google.gson.annotations.SerializedName;

/*

See MultiGame for details of what this class holds

*/
public class MultiMatchDetails {
	private String name;

	@SerializedName("match_id")
	private String matchId;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("end_time")
	private String endTime;

	public String getName() {
		return name;
	}

	public String getMatchId() {
		return matchId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
}
