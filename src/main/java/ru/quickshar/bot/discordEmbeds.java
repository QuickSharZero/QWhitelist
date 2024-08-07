package ru.quickshar.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import ru.quickshar.QWhitelist;
import ru.quickshar.database.Database;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class discordEmbeds {

    public static EmbedBuilder infoEmbed(String senderID){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.info.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.info.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.info.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            if(line.contains("{nickname}")){
                line = line.replace("{nickname}", nicknameReplace(senderID));
            }
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder playEmbed(String senderID){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.play.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.play.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.play.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            if(line.contains("{nickname}")){
                line = line.replace("{nickname}", "" +nicknameReplace(senderID));
            }
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder playNicknameAlreadyExists(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.playErrors.nicknameAlreadyExists.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.playErrors.nicknameAlreadyExists.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.playErrors.nicknameAlreadyExists.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder playDiscordAlreadyExists(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.playErrors.discordAlreadyExists.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.playErrors.discordAlreadyExists.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.playErrors.discordAlreadyExists.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    private static String nicknameReplace(String id){
        try{
            if(!getDatabase().checkDiscord(id)){ return "unknown"; }
            return getDatabase().getNicknameByDiscordID(id);
        }catch(SQLException e) { e.printStackTrace(); }
        return "error";
    }

    private static FileConfiguration getConfig() { return QWhitelist.getInstance().getConfig(); }
    private static Database getDatabase() { return QWhitelist.getInstance().getDatabase(); }
}
