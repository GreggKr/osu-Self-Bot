package me.sirgregg.osubot.util.helpers;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.Color;

public class EmbedUtil {
	public static MessageEmbed createEmbed(Color color, String description) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(color).setDescription(description);
		return embedBuilder.build();
	}
}
