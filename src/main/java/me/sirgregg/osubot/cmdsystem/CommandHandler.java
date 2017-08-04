package me.sirgregg.osubot.cmdsystem;

import me.sirgregg.osubot.OsuBot;
import me.sirgregg.osubot.cmdsystem.commands.BeatmapCommand;
import me.sirgregg.osubot.cmdsystem.commands.UserCommand;
import me.sirgregg.osubot.util.config.Configuration;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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

        if(!raw[0].toLowerCase().startsWith(configuration.getLead())) return;

        String keyword = raw[0].toLowerCase().replaceAll(configuration.getLead(), "");
        for (Command command : commands) {
        	List<String> keywords = Arrays.asList(command.getKeywords());
        	if (keywords.contains(keyword.toLowerCase())) {
        		command.execute(e, args);
			}
		}
    }

}
