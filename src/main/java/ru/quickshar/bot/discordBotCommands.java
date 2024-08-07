package ru.quickshar.bot;


import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.quickshar.QWhitelist;
import ru.quickshar.database.Database;

import java.sql.SQLException;


public class discordBotCommands extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        String senderID = event.getMember().getId();

        switch (event.getName()){
            case "info": {
                event.replyEmbeds(discordEmbeds.infoEmbed(senderID).build())
                        .setEphemeral(true)
                        .queue();
                break;
            }
            case "play": {
                String nickname = event.getOption("nickname", OptionMapping::getAsString);
                try{
                    if(getDatabase().checkDiscord(senderID)){
                        event.replyEmbeds(discordEmbeds.playDiscordAlreadyExists().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    } else if (getDatabase().checkPlayer(nickname)) {
                        event.replyEmbeds(discordEmbeds.playNicknameAlreadyExists().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    }

                    getDatabase().addPlayer(nickname, senderID);
                    event.replyEmbeds(discordEmbeds.playEmbed(senderID).build())
                            .setEphemeral(true)
                            .queue();

                }catch (SQLException e){ e.printStackTrace();}
                break;
            }
            default: {event.reply("I can't handle that command right now :(").setEphemeral(true).queue();}

        }
    }

    private FileConfiguration getConfig() { return QWhitelist.getInstance().getConfig(); }

    private Database getDatabase(){ return QWhitelist.getInstance().getDatabase(); }
}
