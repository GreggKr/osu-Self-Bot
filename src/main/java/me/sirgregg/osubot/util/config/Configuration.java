package me.sirgregg.osubot.util.config;

import com.google.gson.annotations.SerializedName;

public class Configuration {
    @SerializedName("discord_token")
    private String discordToken;

    @SerializedName("osu_token")
	private String osuToken;

    private String lead;

    public String getLead() {
        return lead;
    }

	public String getOsuToken() {
		return osuToken;
	}

	public String getDiscordToken() {
        return discordToken;
    }
}
