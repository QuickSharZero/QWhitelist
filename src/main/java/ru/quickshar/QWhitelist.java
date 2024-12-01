package ru.quickshar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.quickshar.bot.discordBot;
import ru.quickshar.bot.discordBotEvents;
import ru.quickshar.commands.getCodeCommand;
import ru.quickshar.commands.qwhitelist;
import ru.quickshar.database.Database;
import ru.quickshar.events.join;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class QWhitelist extends JavaPlugin {

    private static QWhitelist instance;
    private Database database;

    @Override
    public void onEnable() {
        //Config
        saveDefaultConfig();
        instance = this;

        //Folder
        File file = getDataFolder();
        boolean flag = file.mkdir();
        if(flag) getLogger().info("Folder had been created.");
        else getLogger().info("Folder hadn't been created.");
        //Database
        File db = new File(getDataFolder() + "/database.db");
        if(db.exists()){
            getLogger().info("The database has already been created");
        } else {
            try{
                db.createNewFile();
                getLogger().info("The database has been created");
            }catch(IOException ex){ ex.printStackTrace(); }
        }

        try{
            this.database = new Database();
            database.initializeDatabase();
        }catch(SQLException ex){
            getLogger().info("Unable to connection to database and create tables.");
            ex.printStackTrace();
        }

        //Events
        Bukkit.getPluginManager().registerEvents(new join(), this);
        Bukkit.getPluginManager().registerEvents(new discordBotEvents(), this);
        //Commands
        new qwhitelist();
        new getCodeCommand();

        //DiscordBot
        if(getInstance().getConfig().getBoolean("discordBot.enabled")){
            discordBot.startDiscordBot();
        }

        getLogger().info("=========================");
        getLogger().info("QWhitelist enabled!");
        getLogger().info("Version: " + this.getDescription().getVersion());
        getLogger().info("=========================");
    }

    @Override
    public void onDisable() {
        try{
            this.database.getConnection().close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        if(getConfig().getBoolean("discordBot.enabled")){
            discordBot.shutdownDiscordBot();
        }
        getLogger().info("=========================");
        getLogger().info("QWhitelist disabled!");
        getLogger().info("=========================");
    }


    public static QWhitelist getInstance() { return instance; }

    public Database getDatabase(){ return database; }

}
