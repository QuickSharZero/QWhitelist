package ru.quickshar.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.quickshar.QWhitelist;
import ru.quickshar.Util;

import java.sql.SQLException;

public class join implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerLoginEvent e){
        Player player = e.getPlayer();
        if(Util.getConfig().getBoolean("enable")) {
            try {
                if (!QWhitelist.getInstance().getDatabase().checkPlayer(player.getName())) {
                    e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Util.getMessage("messages.ErrorMessages.NotWhitelist"));
                    return;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
