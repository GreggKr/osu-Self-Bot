package me.sirgregg.osubot.cmdsystem;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.commands.BeatmapCommand;
import me.sirgregg.osubot.cmdsystem.commands.BestCommand;
import me.sirgregg.osubot.cmdsystem.commands.UserCommand;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.config.MessageColor;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler extends ListenerAdapter {
	private static List<Command> commands = new ArrayList<>();
	private Configuration configuration = OsuBot.getConfiguration();

	public CommandHandler() {
		addCommand(new UserCommand());
		addCommand(new BeatmapCommand());
		addCommand(new BestCommand());
	}

	public static List<Command> getCommands() {
		return Collections.unmodifiableList(commands);
	}

	private void addCommand(Command command) {
		if (!commands.contains(command)) {
			commands.add(command);
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!e.getJDA().getSelfUser().equals(e.getAuthor())) return;

		String[] raw = e.getMessage().getContent().split(" ");
		String[] args = Arrays.copyOfRange(raw, 1, raw.length);

		if (!raw[0].toLowerCase().startsWith(configuration.getLead())) return;

		Color color;
		if (e.getChannelType().isGuild()) {
			color = e.getGuild().getSelfMember().getColor();
		} else {
			MessageColor messageColor = configuration.getMessageColor();
			color = new Color(messageColor.getR(), messageColor.getG(), messageColor.getB());
		}

		String keyword = raw[0].toLowerCase().replaceAll(configuration.getLead(), "");
		for (Command command : commands) {
			List<String> keywords = Arrays.asList(command.getKeywords());
			if (keywords.contains(keyword.toLowerCase())) {
				command.execute(e, color, args);
			}
		}
	}

}
