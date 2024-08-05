package ru.quickshar.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.quickshar.QWhitelist;
import ru.quickshar.database.Database;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class discordBotCommands extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        String senderID = event.getMember().getId();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.GREEN);

        switch (event.getName()){
            case "info":
                String infoTitle = getConfig().getString("discordBotEmbeds.info.title");
                List<String> infoDescription = getConfig().getStringList("discordBotEmbeds.info.description");

                embed.setTitle(infoTitle);
                for(String infoLine : infoDescription){
                    if(infoLine.contains("{nickname}")){
                        infoLine = infoLine.replace("{nickname}", nicknameReplace(senderID));
                    }
                    embed.appendDescription(infoLine + "\n");
                }
                event.replyEmbeds(embed.build())
                        .setEphemeral(true)
                        .queue();
                break;
            case "play":
                String playTitle = getConfig().getString("discordBotEmbeds.play.title");
                List<String> playDescription = getConfig().getStringList("discordBotEmbeds.play.description");

                embed.setTitle(playTitle);
                for(String playLine : playDescription){
                    if(playLine.contains("{nickname}")){
                        playLine = playLine.replace("{nickname}", nicknameReplace(senderID));
                    }
                    embed.appendDescription(playLine + "\n");
                }
                event.replyEmbeds(embed.build())
                        .setEphemeral(true)
                        .queue();
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    private String nicknameReplace(String id){
        try{
            if(!getDatabase().checkDiscord(id)){ return "unknown"; }
            return getDatabase().getNicknameByDiscordID(id);
        }catch(SQLException e){ e.printStackTrace(); }
        return "error";
    }

    private FileConfiguration getConfig() { return QWhitelist.getInstance().getConfig(); }

    private Database getDatabase(){ return QWhitelist.getInstance().getDatabase(); }
}
