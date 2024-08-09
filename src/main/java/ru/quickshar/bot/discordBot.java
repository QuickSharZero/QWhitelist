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
                        .addOption(OptionType.STRING, "nickname", "Your minecraft nickname", true),
                Commands.slash("code", "Link minecraft nickname")
                        .addOption(OptionType.INTEGER, "code", "Your 6 digit code", true)
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
        String activity = getConfig().getString("discordBot.activity");
        String activityText = getConfig().getString("discordBot.activityText");
        if(activity.equalsIgnoreCase("watching")){
            jda.getPresence().setActivity(Activity.watching(activityReplacer(activityText)));
        } else if (activity.equalsIgnoreCase("playing")) {
            jda.getPresence().setActivity(Activity.playing(activityReplacer(activityText)));
        } else if (activity.equalsIgnoreCase("nothing")) {
            jda.getPresence().setActivity(null);
        }
        return;
    }

    private static String activityReplacer(String text){
        if(text.contains("{online}")){
            text = text.replace("{online}", "" + QWhitelist.getInstance().getServer().getOnlinePlayers().size());
        }
        return text;
    }

    private static FileConfiguration getConfig(){
        return QWhitelist.getInstance().getConfig();
    }

}
