package ru.quickshar.bot;


import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.quickshar.QWhitelist;
import ru.quickshar.database.Database;

import java.sql.SQLException;
import java.util.Objects;


public class discordBotCommands extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        String senderID = event.getMember().getId();

        switch (event.getName()){
            case "info": {
                QWhitelist.getInstance().debug("DiscordBotCommands.info: start and send info embed");

                event.replyEmbeds(discordEmbeds.infoEmbed(senderID).build())
                        .setEphemeral(true)
                        .queue();
                break;
            }
            case "play": {
                QWhitelist.getInstance().debug("DiscordBotCommands.play: start");

                String nickname = event.getOption("nickname", OptionMapping::getAsString);
                try{
                    if(getDatabase().checkDiscord(senderID)){
                        QWhitelist.getInstance().debug("DiscordBotCommands.play: discordAlreadyExists embed send");

                        event.replyEmbeds(discordEmbeds.discordAlreadyExists().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    }
                    if(getDatabase().checkPlayer(nickname)) {
                        QWhitelist.getInstance().debug("DiscordBotCommands.play: nicknameAlreadyExists embed send");

                        event.replyEmbeds(discordEmbeds.nicknameAlreadyExists().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    }

                    getDatabase().addPlayer(nickname, senderID);
                    if(getConfig().getBoolean("discordBot.role.addRole")){
                        QWhitelist.getInstance().debug("DiscordBotCommands.play: addRole function start");

                        try {
                            event.getGuild().addRoleToMember(
                                    event.getUser(),
                                    event.getGuild().getRoleById(QWhitelist.getInstance().getConfig().getString("discordBot.role.roleID"))
                            ).queue();
                        } catch (Exception e){ event.reply("Failed to add role. \nPlease report the error to server administrators or moderators"); }

                        QWhitelist.getInstance().debug("DiscordBotCommands.play: addRole function complete");
                    }
                    event.replyEmbeds(discordEmbeds.playEmbed(senderID).build())
                            .setEphemeral(true)
                            .queue();

                }catch (SQLException e){ e.printStackTrace();}
                break;
            }
            case "code":{
                QWhitelist.getInstance().debug("DiscordBotCommands.code: start");

                Integer code = event.getOption("code", OptionMapping::getAsInt);
                try {
                    if(getDatabase().checkDiscord(senderID)){
                        QWhitelist.getInstance().debug("DiscordBotCommands.code: discordAlreadyExists embed send");
                        event.replyEmbeds(discordEmbeds.discordAlreadyExists().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    }
                    if(!getDatabase().checkCode(code)) {
                        QWhitelist.getInstance().debug("DiscordBotCommands.code: codeNotExists embed send");
                        event.replyEmbeds(discordEmbeds.codeNotExists().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    }
                    if(getDatabase().checkNicknameLinkedByCode(code)){
                        QWhitelist.getInstance().debug("DiscordBotCommands.code: nicknameAlreadyLinked embed send");
                        event.replyEmbeds(discordEmbeds.nicknameAlreadyLinked().build())
                                .setEphemeral(true)
                                .queue();
                        return;
                    }
                    QWhitelist.getInstance().debug("DiscordBotCommands.code: All checks passed");
                    QWhitelist.getInstance().debug("DiscordBotCommands.code: start setDiscordIDByCode function");
                    getDatabase().setDiscordIDByCode(code, senderID);
                    event.replyEmbeds(discordEmbeds.nicknameLinked().build())
                            .setEphemeral(true)
                            .queue();
                    return;
                }catch(SQLException e) { e.printStackTrace(); }
                break;
            }
            default: {event.reply("I can't handle that command right now :(").setEphemeral(true).queue();}

        }
    }

    private FileConfiguration getConfig() { return QWhitelist.getInstance().getConfig(); }

    private Database getDatabase(){ return QWhitelist.getInstance().getDatabase(); }
}
