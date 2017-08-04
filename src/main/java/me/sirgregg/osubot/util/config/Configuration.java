package me.sirgregg.osubot.util.config;

import com.google.gson.annotations.SerializedName;

public class Configuration {
	private String lead, color;

    @SerializedName("discord_token")
    private String discordToken;

    @SerializedName("osu_token")
	private String osuToken;

    public String getLead() {
        return lead;
    }

	public String getColor() {
		return color;
	}

	public String getOsuToken() {
		return osuToken;
	}

	public String getDiscordToken() {
        return discordToken;
    }
}
