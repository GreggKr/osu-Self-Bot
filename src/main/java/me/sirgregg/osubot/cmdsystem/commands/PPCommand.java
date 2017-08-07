package me.sirgregg.osubot.cmdsystem.commands;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.Command;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.helpers.EmbedUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;

public class PPCommand extends Command {
	private Configuration configuration = OsuBot.getConfiguration();
	public PPCommand() {
		super("pp", "pp <beatmapID> <mode> <acc> <mods OR nm> <combo OR max> <misses OR none> [score version]", "Displays how much PP a play is worth.");
	}

	@Override
	public void execute(MessageReceivedEvent e, Color color, String[] args) {
		// 0 -> id
		// 1 -> mode
		// 2 -> accuracy
		// 3 -> mods
		// 4 -> combo
		// 5 -> misses
		// 6 -> scoring version
		if (args.length < 6) { // id, mode, acc, mods, combo, misses
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + configuration.getLead() + getUsage())).queue();
			return;
		}

		String rawId = args[0];
		String rawMode = args[1];

		int id = -1;
		try {
			id = Integer.parseInt(rawId);
		} catch (NumberFormatException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + configuration.getLead() + getUsage())).queue();
			return;
		}

		String mode = OsuBot.getOsu().formatMode(rawMode);

		if (mode == null) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color,
					"**Valid Modes: **\n" +
							"std, standard -> osu!standard\n" +
							"tak, taiko -> Taiko" + "\n" +
							"ctb, catch -> Catch the Beat" + "\n" +
							"man, mania -> Mania\n")).queue();
			return;
		}

		String rawAcc = args[2];
		String mods = args[3];
		String combo = args[4];
		String misses = args[5];

		String scoreVersion = "scorev1";

		if (args.length >= 7 && args[6].equalsIgnoreCase("scorev2")) {
			scoreVersion = "scorev2";
		}

		Beatmap beatmap = OsuBot.getOsu().getBeatmap(rawId, rawMode);

		double acc;
		try {
			acc = Double.parseDouble(rawAcc);
		} catch (NumberFormatException ex) {
			e.getMessage().editMessage(EmbedUtil.createEmbed(color, "**Correct Usage:**\n" + getUsage())).queue();
			return;
		}

		if (mods.equalsIgnoreCase("nm")) {
			mods = "nomod";
		}

		if (combo.equalsIgnoreCase("max")) {
			combo = beatmap.getMaxCombo();
		}

		if (misses.equalsIgnoreCase("none")) {
			misses = "0";
		}

		String pp = OsuBot.getOsu().getPP(rawId, rawMode, rawAcc, mods, combo, misses, scoreVersion);

		pp = pp.substring(0, pp.length() - 3);

		e.getMessage().editMessage(EmbedUtil.createEmbed(color,
				"**CREATOR: **" + beatmap.getCreator() + "\n" +
						"**VERSION: **" + beatmap.getVersion() + "\n" +
						"**COMPOSER: **" + beatmap.getArtist() + "\n" +
						"**SONG: **" + beatmap.getTitle() + "\n" +
						"**PP: **" + pp
		)).queue();

	}
}
