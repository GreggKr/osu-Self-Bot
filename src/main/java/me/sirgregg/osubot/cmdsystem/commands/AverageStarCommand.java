package me.sirgregg.osubot.cmdsystem.commands;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.Command;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.helpers.EmbedUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import me.sirgregg.osubot.util.objects.Play;
import me.sirgregg.osubot.util.objects.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;

public class AverageStarCommand extends Command {
	private Configuration configuration = OsuBot.getConfiguration();
	public AverageStarCommand() {
		super(new String[] { "averagestars", "avgstar", "astar" }, "avgstar <username> <mode> <amount>", "Shows the average stars of a users's most recent beatmaps.");
	}

	@Override
	public void execute(MessageReceivedEvent e, Color color, String[] args) {
		// 0 -> username
		// 1 -> mode
		// 2 -> amount

		if (args.length < 3) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + configuration.getLead() + getUsage())).queue();
			return;
		}

		String username = args[0];
		String rawMode = args[1];
		String strNumber = args[2];

		int number;
		try {
			number = Integer.parseInt(strNumber);

			if (number < 1 || number > 100) {
				e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Invalid Number**\nThe amount of beatmaps to parse must be between 1 and 100.")).queue();
				return;
			}
		} catch (NumberFormatException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + getUsage())).queue();
			return;
		}

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

		double totalStarCount = 0;
		Play[] plays = OsuBot.getOsu().getRecentPlays(username, Integer.toString(number), rawMode);
		for (Play play : plays) {
			Beatmap beatmap = OsuBot.getOsu().getBeatmap(play.getBeatmapId(), rawMode);
			totalStarCount += Double.parseDouble(beatmap.getDifficultyRating());
		}

		User user = OsuBot.getOsu().getUser(Integer.parseInt(plays[0].getUserId()), rawMode);

		e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**PLAYER: **" + user.getUsername() + "\n" +
				"**AMOUNT: **" + Integer.toString(number) + "\n" +
				"**AVERAGE STARS: **" + Double.toString(totalStarCount) + "*"
		)).queue();
	}
}
