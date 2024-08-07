package ru.quickshar.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import ru.quickshar.QWhitelist;

import java.util.Collections;

public class discordBot {
    private static JDA jda;

    public static void startDiscordBot(){
        jda = JDABuilder.createDefault(getConfig().getString("discordBot.token"), Collections.emptyList())
                .addEventListeners(new discordBotCommands())
                .disableCache(
                        CacheFlag.EMOJI,
                        CacheFlag.STICKER,
                        CacheFlag.VOICE_STATE,
                        CacheFlag.SCHEDULED_EVENTS)
                .build();

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(
                Commands.slash("info", "Server info message"),
                Commands.slash("play", "Add to whitelist")
                        .addOption(OptionType.STRING, "nickname", "Your minecraft nickname", true)
        );
        commands.queue();

        try{
            jda.awaitReady();
            updateDiscordBotActivity();
            QWhitelist.getInstance().getLogger().info("DiscordBot LAUNCHED!");
        } catch(InterruptedException e){
            QWhitelist.getInstance().getLogger().warning("" + e);
        }
    }

    public static void shutdownDiscordBot() {
        jda.shutdown();
        try{
            jda.awaitShutdown();
            QWhitelist.getInstance().getLogger().info("Discord Bot disabled.");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void updateDiscordBotActivity(){
        jda.getPresence().setActivity(Activity.watching("" + QWhitelist.getInstance().getServer().getOnlinePlayers().size()));
    }

    private static FileConfiguration getConfig(){
        return QWhitelist.getInstance().getConfig();
    }

}
