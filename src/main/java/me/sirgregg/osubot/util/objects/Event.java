package me.sirgregg.osubot.util.objects;

import com.google.gson.annotations.SerializedName;

public class Event {
	private String date;
	@SerializedName("display_html")
	private String displayHTML;

	@SerializedName("beatmap_id")
	private String beatmapId;

	@SerializedName("beatmapset_id")
	private String beatmapsetId;

	@SerializedName("epicfactor")
	private String epicFactor;

	public String getDate() {
		return date;
	}

	public String getDisplayHTML() {
		return displayHTML;
	}

	public String getBeatmapId() {
		return beatmapId;
	}

	public String getBeatmapsetId() {
		return beatmapsetId;
	}

	public String getEpicFactor() {
		return epicFactor;
	}
}
