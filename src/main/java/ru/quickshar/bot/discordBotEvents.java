package ru.quickshar.bot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.quickshar.QWhitelist;

public class discordBotEvents implements Listener {
    public discordBotEvents(){
    }

    @EventHandler
    public void joinUpdateDiscordBotActivity(PlayerJoinEvent event){
        discordBot.updateDiscordBotActivity();
    }

    @EventHandler
    public void leaveUpdateDiscordActivity(PlayerQuitEvent event){
        (new BukkitRunnable() {
            @Override
            public void run() {
                discordBot.updateDiscordBotActivity();
            }
        }).runTaskLater(QWhitelist.getInstance(), 20L);
    }
}
