package ru.quickshar.bot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.quickshar.QWhitelist;

public class discordBotEvents implements Listener {

    @EventHandler
    public void joinUpdateDiscordBotActivity(PlayerJoinEvent event){
        if(!getConfig().getBoolean("discordBot.enabled")) { return; }

        String activity = QWhitelist.getInstance().getConfig().getString("discordBot.activity");
        String activityText = QWhitelist.getInstance().getConfig().getString("discordBot.activityText");

        if(activity.equalsIgnoreCase("nothing")) { return; }
        if(activityText.contains("{online}")){
            discordBot.updateDiscordBotActivity();
            return;
        }
    }

    @EventHandler
    public void leaveUpdateDiscordActivity(PlayerQuitEvent event){
        if(!getConfig().getBoolean("discordBot.enabled")) { return; }

        String activity = QWhitelist.getInstance().getConfig().getString("discordBot.activity");
        String activityText = QWhitelist.getInstance().getConfig().getString("discordBot.activityText");

        if(activity.equalsIgnoreCase("nothing")) { return; }
        if(activityText.contains("{online}")){
            (new BukkitRunnable() {
                @Override
                public void run() {
                    discordBot.updateDiscordBotActivity();
                }
            }).runTaskLater(QWhitelist.getInstance(), 20L);
            return;
        }
    }

    private FileConfiguration getConfig(){ return QWhitelist.getInstance().getConfig(); }
}
