package me.sirgregg.osubot.cmdsystem.commands;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.Command;
import me.sirgregg.osubot.cmdsystem.CommandHandler;
import me.sirgregg.osubot.util.config.Configuration;
import me.sirgregg.osubot.util.helpers.EmbedUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {
	private Configuration configuration = OsuBot.getConfiguration();
	public HelpCommand() {
		super("help", "help", "Shows a list of commands.");
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		StringBuilder sb = new StringBuilder();
		for (Command command : CommandHandler.getCommands()) {
			sb.append(configuration.getLead()).append(command.getUsage()).append(" - ").append(command.getDescription()).append("\n");
		}
		String desc = sb.toString();
		e.getMessage().editMessage(EmbedUtil.createEmbed(e.getGuild().getSelfMember().getColor(), desc)).queue();
	}
}
