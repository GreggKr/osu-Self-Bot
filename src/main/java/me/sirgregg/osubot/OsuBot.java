package me.sirgregg.osubot;

import me.sirgregg.osubot.cmdsystem.CommandHandler;
import me.sirgregg.osubot.util.Osu;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.config.ConfigurationHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class OsuBot {
	private static Osu osu;
	private static JDA jda;
	private static ConfigurationHandler configurationHandler = new ConfigurationHandler();

	public static void main(String[] args) {
		configurationHandler.setupJson();
		configurationHandler.parseJson();

		osu = new Osu();

		try {
			jda = new JDABuilder(AccountType.CLIENT)
					.setToken(configurationHandler.getConfiguration().getDiscordToken())
					.setAutoReconnect(true)
					.addEventListener(new CommandHandler())
					.buildBlocking();
		} catch (IllegalArgumentException | InterruptedException | LoginException | RateLimitedException e) {
			e.printStackTrace();
		}
	}

	public static Configuration getConfiguration() {
		return configurationHandler.getConfiguration();
	}

	public static Osu getOsu() {
		return osu;
	}
}
