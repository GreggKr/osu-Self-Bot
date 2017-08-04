package me.sirgregg.osubot.util.objects;

import com.google.gson.annotations.SerializedName;
import me.sirgregg.osubot.util.objects.Event;

public class User {
	private String username, count300, count100, count50, level, accuracy, country;
	private Event[] events;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("playcount")
	private String playCount;

	@SerializedName("ranked_score")
	private String rankedScore;

	@SerializedName("total_score")
	private String totalScore;

	@SerializedName("pp_rank")
	private String ppRank;

	@SerializedName("pp_raw")
	private String ppRaw;

	@SerializedName("count_rank_ss")
	private String countRankSS;

	@SerializedName("count_rank_s")
	private String countRankS;

	@SerializedName("count_rank_a")
	private String countRankA;

	@SerializedName("pp_country_rank")
	private String ppCountryRank;

	public String getUsername() {
		return username;
	}

	public String getCount300() {
		return count300;
	}

	public String getCount100() {
		return count100;
	}

	public String getCount50() {
		return count50;
	}

	public String getLevel() {
		return level;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public String getCountry() {
		return country;
	}

	public Event[] getEvents() {
		return events;
	}

	public String getUserId() {
		return userId;
	}

	public String getPlayCount() {
		return playCount;
	}

	public String getRankedScore() {
		return rankedScore;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public String getPpRank() {
		return ppRank;
	}

	public String getPpRaw() {
		return ppRaw;
	}

	public String getCountRankSS() {
		return countRankSS;
	}

	public String getCountRankS() {
		return countRankS;
	}

	public String getCountRankA() {
		return countRankA;
	}

	public String getPpCountryRank() {
		return ppCountryRank;
	}
}
