package me.sirgregg.osubot.cmdsystem.commands;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.Command;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.helpers.EmbedUtil;
import me.sirgregg.osubot.util.objects.Beatmap;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.List;

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

		List<String> info = OsuBot.getOsu().getInfo(rawId, rawMode, rawAcc, mods, combo, misses, scoreVersion);
		String pp = info.get(28);

		pp = pp.substring(0, pp.length() - 3);

		String stars = info.get(19);
		stars = stars.substring(0, stars.length() - 6);// length of " stars"

		String odArCs = info.get(12); // od7.5 ar9 cs4.3

		String[] split = odArCs.split(" "); // 0 -> od, 1 -> ar, 2 -> cs
		String od = split[0].substring(2);
		String ar = split[1].substring(2);
		String cs = split[2].substring(2);
		cs = cs.substring(0, cs.length() - 1); // line break


		String aimPP = info.get(22);
		aimPP = aimPP.substring(5, aimPP.length());

		String speedPP = info.get(23);
		speedPP = speedPP.substring(7, speedPP.length());

		String accPP = info.get(24);
		accPP = accPP.substring(10, accPP.length());
		int parsedMode = OsuBot.getOsu().parseMode(rawMode);

		e.getMessage().editMessage(EmbedUtil.createEmbed(color,
				"**PP: **" + pp + "\n" +
						"**AIM: **" + aimPP +
						"**SPEED: **" + speedPP +
						"**ACCURACY: **" + accPP + "\n" +

						"**MODS: **+" + mods.toUpperCase() + "\n\n" +

						"**ACCURACY: **" + rawAcc + "%\n" +
						"**COMBO: **" + combo + "x/" + beatmap.getMaxCombo() + "x (" + misses + "MISS)\n\n" +

						"**STARS: **" + stars + "*\n" +
						"**CIRCLE SIZE: **CS" + cs + "\n" +
						"**OVERALL DIFFICULTY: **OD" + od + "\n" +
						"**APPROACH RATE: **AR" + ar + "\n" +
						"**HEALTH: **HP" + "\n\n" +

						"**BEATMAP: **https://osu.ppy.sh/b/" + beatmap.getBeatmapId() + "\n" +
						"**MAPPER: **" + beatmap.getCreator() + "\n" +
						"**VERSION: **" + beatmap.getVersion() + "\n\n" +

						"**COMPOSER: **" + beatmap.getArtist() + "\n" +
						"**MUSIC: **" + beatmap.getTitle() + "\n\n" +

						"**COMMAND: **osu!PP " + beatmap.getBeatmapId() + "?m=" + parsedMode + " " + mods + " " + acc + " " + combo + " " + misses + " " + scoreVersion
		)).queue();

	}
}
