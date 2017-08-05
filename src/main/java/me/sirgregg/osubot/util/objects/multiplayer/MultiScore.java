package me.sirgregg.osubot.util.objects.multiplayer;

import com.google.gson.annotations.SerializedName;

/*

See MultiGame for details of what this class holds

*/
public class MultiScore {
	private String slot, team, score, rank, count50, count100, count300, perfect, pass;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("maxcombo")
	private String maxCombo;

	@SerializedName("countmiss")
	private String countMiss;

	@SerializedName("countgeki")
	private String countGeki;

	@SerializedName("countkatu")
	private String countKatu;

	public String getSlot() {
		return slot;
	}

	public String getTeam() {
		return team;
	}

	public String getScore() {
		return score;
	}

	public String getRank() {
		return rank;
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

	public String getPass() {
		return pass;
	}

	public String getUserId() {
		return userId;
	}

	public String getMaxCombo() {
		return maxCombo;
	}

	public String getCountMiss() {
		return countMiss;
	}

	public String getCountGeki() {
		return countGeki;
	}

	public String getCountKatu() {
		return countKatu;
	}
}
