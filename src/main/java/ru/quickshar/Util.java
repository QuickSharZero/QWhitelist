package ru.quickshar;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.Random;

public class Util {

    public static String getMessage(String key){
        String message = QWhitelist.getInstance().getConfig().getString(key);
        if(message == null){
            QWhitelist.getInstance().getLogger().info(key);
            return "Message not found";
        }
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    public static FileConfiguration getConfig(){
        return QWhitelist.getInstance().getConfig();
    }

    public static int generateCode(){
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min + 1) + min;
    }

}
