package ru.quickshar.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.quickshar.QWhitelist;

import java.util.List;

public class discordBotCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        switch (event.getName()){
            case "info":
                String embedTitle = getConfig().getString("discordBotEmbeds.info.title");
                List<String> embedDescription = getConfig().getStringList("discordBotEmbeds.info.description");

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(embedTitle);
                for(String line : embedDescription){
                    embed.appendDescription(line + "\n");
                }
                event.replyEmbeds(embed.build())
                        .setEphemeral(true)
                        .queue();
                break;
            case "play":
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    private FileConfiguration getConfig() { return QWhitelist.getInstance().getConfig(); }
}
