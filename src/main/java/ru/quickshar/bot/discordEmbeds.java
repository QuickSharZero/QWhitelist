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
                line = line.replace("{nickname}", nicknameReplace(senderID));
            }
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder nicknameAlreadyExists(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.errors.nicknameAlreadyExists.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.errors.nicknameAlreadyExists.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.errors.nicknameAlreadyExists.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder discordAlreadyExists(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.errors.discordAlreadyExists.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.errors.discordAlreadyExists.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.errors.discordAlreadyExists.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder codeNotExists(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.errors.codeNotExists.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.errors.codeNotExists.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.errors.codeNotExists.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder addRoleError(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.errors.addRoleError.title");
        List<String> description = getConfig().getStringList("discrodBotEmbeds.errors.addRoleError.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.errors.addRoleError.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder nicknameAlreadyLinked(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.errors.nicknameAlreadyLinked.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.errors.nicknameAlreadyLinked.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.errors.nicknameAlreadyLinked.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    public static EmbedBuilder nicknameLinked(){
        EmbedBuilder embed = new EmbedBuilder();
        String title = getConfig().getString("discordBotEmbeds.code.title");
        List<String> description = getConfig().getStringList("discordBotEmbeds.code.description");
        Color color = Color.decode(Objects.requireNonNull(getConfig().getString("discordBotEmbeds.code.color")));

        embed.setTitle(title);
        embed.setColor(color);
        for(String line : description){
            embed.appendDescription(line + "\n");
        }

        return embed;
    }

    private static String nicknameReplace(String id){
        try{
            if(!getDatabase().checkDiscord(id)){ return "[NOT LINKED]"; }
            return getDatabase().getNicknameByDiscordID(id);
        }catch(SQLException e) { e.printStackTrace(); }
        return "[ERROR]";
    }

    private static FileConfiguration getConfig() { return QWhitelist.getInstance().getConfig(); }
    private static Database getDatabase() { return QWhitelist.getInstance().getDatabase(); }
}
