package me.sirgregg.osubot.cmdsystem.commands;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.Command;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.helpers.CountryCode;
import me.sirgregg.osubot.util.helpers.EmbedUtil;
import me.sirgregg.osubot.util.objects.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;

import static me.sirgregg.osubot.util.helpers.NumbHelper.format;

public class UserCommand extends Command {
	private Configuration configuration = OsuBot.getConfiguration();
	public UserCommand() {
		super("user", "user <username> <mode> [more]", "Tests if it works.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		Color color = e.getGuild().getSelfMember().getColor();
		// 0 -> username
		// 1 -> mode
		if (args.length < 2) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + configuration.getLead() + getUsage())).queue();
			return;
		}

		boolean more = false;

		if (args.length >= 3 && args[2].equalsIgnoreCase("more")) { // If it's more than the base args and the 3rd arg (args[2]) is "more"
			more = true;
		}

		String username = args[0];
		String rawMode = args[1];

		String mode = OsuBot.getOsu().formatMode(rawMode);

		if (mode == null) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
					"**Valid Modes: **\n" +
							"std, standard -> osu!standard\n" +
							"tak, taiko -> Taiko" +
							"ctb, catch -> Catch the Beat" +
							"man, mania -> Mania\n")).queue();
			return;
		}

		User user = OsuBot.getOsu().getUser(username, rawMode);
		if (user == null) { // Mode is invalid or User does not have stats
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
					"**Failed to load statistics: **\n" +
							"That user does not have statistics for that mode.")).queue();
			return;
		}

		if (!more) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
							"**MODE: **" + mode + "\n" +
							"**USERNAME: **" + user.getUsername() + ", " + CountryCode.getByCode(user.getCountry()).getName() + "\n" +
							"**RANK: **#" + format(user.getPpRank()) + ", (Country Rank: #" + format(user.getPpCountryRank()) + ")\n" +
							"**TOTAL PP: **" + user.getPpRaw() + "\n" +
							"**RANKED SCORE: **" + format(user.getRankedScore()) + "\n\n" +

							"**PROFILE: **<https://osu.ppy.sh/u/" + user.getUserId() + ">"
			)).queue();
		} else {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
							"**MODE: **" + mode + "\n" +
							"**USERNAME: **" + user.getUsername() + ", " + CountryCode.getByCode(user.getCountry()).getName() + "\n" +

							"**RANK: **#" + format(user.getPpRank()) + ", (Country Rank: #" + format(user.getPpCountryRank()) + ")\n" +
							"**TOTAL PP: **" + user.getPpRaw() + "\n" +
							"**RANKED SCORE: **" + format(user.getRankedScore()) + "\n" +
							"**ACCURACY: **" + user.getAccuracy() + "\n\n" +

							"**PLAY COUNT: **" + format(user.getPlayCount()) + "\n" +
							"**SCORES: **A: " + format(user.getCountRankA()) + ", S: " + format(user.getCountRankS()) + ", SS (X): " + user.getCountRankSS() + "\n\n" +

							"**PROFILE: **<https://osu.ppy.sh/u/" + user.getUserId() + ">"
			)).queue();
		}
	}
}
