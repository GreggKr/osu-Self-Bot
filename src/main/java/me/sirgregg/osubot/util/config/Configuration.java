package me.sirgregg.osubot.util.config;

import com.google.gson.annotations.SerializedName;

public class Configuration {
	private String lead;

	@SerializedName("path_to_oppai")
	private String oppaiPath;

	@SerializedName("message_color")
	private MessageColor messageColor;

    @SerializedName("discord_token")
    private String discordToken;

    @SerializedName("osu_token")
	private String osuToken;

	public MessageColor getMessageColor() {
		return messageColor;
	}

	public String getLead() {
		return lead;
	}

	public String getOsuToken() {
		return osuToken;
	}

	public String getDiscordToken() {
        return discordToken;
    }

	public String getOppaiPath() {
		return oppaiPath;
	}
}
