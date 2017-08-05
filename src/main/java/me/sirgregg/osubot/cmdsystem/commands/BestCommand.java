package me.sirgregg.osubot.cmdsystem.commands;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.Command;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.helpers.EmbedUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;

public class BestCommand extends Command {
	private Configuration configuration = OsuBot.getConfiguration();
	public BestCommand() {
		super("best", "best <username> <mode> <number> [more]", "Displays a users's <number>th best score.");
	}

	@Override
	public void execute(MessageReceivedEvent e, Color color, String[] args) {
		// 0 -> Username
		// 1 -> mode
		// 2 -> number
		// 3 -> [more]

		if (args.length < 3) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + configuration.getLead() + getUsage())).queue();
			return;
		}

		boolean more = false;

		if (args.length >= 4 && args[3].equalsIgnoreCase("more")) { // If it's more than the base args and the 3rd arg (args[2]) is "more"
			more = true;
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
		} catch (NumberFormatException ex){
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


		Beatmap beatmap = OsuBot.getOsu().getXthBestPlay(username, number, rawMode);
		if (beatmap == null) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
					"**Failed to load statistics: **\n" +
							"That user might not have enough plays.")).queue();
			return;
		}

		e.getMessage().editMessage("*: " + beatmap.getDifficultyRating()).queue();
	}
}
