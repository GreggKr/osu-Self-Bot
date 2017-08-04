package me.sirgregg.osubot.util.objects;

import com.google.gson.annotations.SerializedName;

public class Beatmap {
	private String approved, artist, bpm, creator, source, title, version, mode, tags;

	@SerializedName("approved_date")
	private String approvedDate;

	@SerializedName("last_update")
	private String lastUpdate;

	@SerializedName("beatmap_id")
	private String beatmapId;

	@SerializedName("beatmapset_id")
	private String beatmapsetId;

	@SerializedName("difficultyrating")
	private String difficultyRating;

	@SerializedName("diff_size")
	private String difCircleSize;

	@SerializedName("diff_overall")
	private String difOverall;

	@SerializedName("diff_approach")
	private String diffApproach;

	@SerializedName("diff_drain")
	private String diffDrain;

	@SerializedName("hit_length")
	private String hitLength;

	@SerializedName("genre_id")
	private String genreId;

	@SerializedName("language_id")
	private String languageId;

	@SerializedName("total_length")
	private String totalLength;

	@SerializedName("file_md5")
	private String fileMD5;

	@SerializedName("favourite_count")
	private String favoriteCount;

	@SerializedName("playcount")
	private String playCount;

	@SerializedName("passcount")
	private String passCount;

	@SerializedName("max_combo")
	private String maxCombo;

	public String getApproved() {
		return approved;
	}

	public String getArtist() {
		return artist;
	}

	public String getBpm() {
		return bpm;
	}

	public String getCreator() {
		return creator;
	}

	public String getSource() {
		return source;
	}

	public String getTitle() {
		return title;
	}

	public String getVersion() {
		return version;
	}

	public String getMode() {
		return mode;
	}

	public String getTags() {
		return tags;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public String getBeatmapId() {
		return beatmapId;
	}

	public String getBeatmapsetId() {
		return beatmapsetId;
	}

	public String getDifficultyRating() {
		return difficultyRating;
	}

	public String getDifCircleSize() {
		return difCircleSize;
	}

	public String getDifOverall() {
		return difOverall;
	}

	public String getDiffApproach() {
		return diffApproach;
	}

	public String getDiffDrain() {
		return diffDrain;
	}

	public String getHitLength() {
		return hitLength;
	}

	public String getGenreId() {
		return genreId;
	}

	public String getLanguageId() {
		return languageId;
	}

	public String getTotalLength() {
		return totalLength;
	}

	public String getFileMD5() {
		return fileMD5;
	}

	public String getFavoriteCount() {
		return favoriteCount;
	}

	public String getPlayCount() {
		return playCount;
	}

	public String getPassCount() {
		return passCount;
	}

	public String getMaxCombo() {
		return maxCombo;
	}
}
