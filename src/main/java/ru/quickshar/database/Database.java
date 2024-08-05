package ru.quickshar.database;

import ru.quickshar.QWhitelist;
import ru.quickshar.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database {

    private Connection conn;

    public Connection getConnection() throws SQLException {

        if(conn != null && !conn.isClosed() && conn.isValid(360)){
            return conn;
        }

        Connection conn = null;

        if(Util.getConfig().getBoolean("database.enable")){
            String hostname = Util.getConfig().getString("database.host");
            int port = Util.getConfig().getInt("database.port");
            String database_name = Util.getConfig().getString("database.database_name");
            String username = Util.getConfig().getString("database.username");
            String password = Util.getConfig().getString("database.password");

            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database_name;
            this.conn = DriverManager.getConnection(url, username, password);
            QWhitelist.getInstance().getLogger().info("Connected to the database");
            return this.conn;
        }
        String url = "jdbc:sqlite:" + QWhitelist.getInstance().getDataFolder() + "/database.db";
        this.conn = DriverManager.getConnection(url);
        QWhitelist.getInstance().getLogger().info("Connected to the database");
        return this.conn;
    }

    public void initializeDatabase() throws SQLException{
        Statement state = getConnection().createStatement();

        state.execute("CREATE TABLE IF NOT EXISTS whitelist(nickname VARCHAR(16) , discord VARCHAR(255), code INT, PRIMARY KEY (nickname))");
        QWhitelist.getInstance().getLogger().info("Created the whitelist table in database.");

        state.close();
    }

    public boolean checkPlayer(String nickname) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("SELECT nickname FROM whitelist WHERE nickname = ?");
        statement.setString(1, nickname);
        ResultSet result = statement.executeQuery();
        if(result.next()){ statement.close(); return true; }
        statement.close();
        return false;
    }

    public boolean checkDiscord(String discordID) throws SQLException{
        if(discordID.equalsIgnoreCase("null")){ return false; }
        PreparedStatement statement = getConnection().prepareStatement("SELECT discord FROM whitelist WHERE discord = ?");
        statement.setString(1, discordID);
        ResultSet result = statement.executeQuery();
        if(result.next()){ statement.close(); return true; }
        statement.close();
        return false;
    }

    public void updateNickname(String discord, String nickname) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("UPDATE whitelist SET nickname = ? WHERE discord = ?");
        statement.setString(1, nickname);
        statement.setString(2, discord);
        statement.executeUpdate();
        statement.close();
    }

    public void addPlayer(String nickname, String discordID) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO whitelist(nickname, discord, code) VALUES (?, ?, ?)");
        statement.setString(1, nickname);
        statement.setString(2, discordID);
        statement.setInt(3, Util.generateCode());
        statement.executeUpdate();
        statement.close();
    }

    public void removePlayer(String nickname) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM whitelist WHERE nickname = ?");
        statement.setString(1, nickname);
        statement.executeUpdate();
        statement.close();
    }

    public int getPlayerCode(String nickname) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("SELECT code FROM whitelist WHERE nickname = ?");
        statement.setString(1, nickname);
        ResultSet result = statement.executeQuery();
        if(result.next()){ return result.getInt("code"); }
        return 0;
    }

    public ArrayList<String> nicknamesList() throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("SELECT nickname FROM whitelist");
        ResultSet result = statement.executeQuery();
        ArrayList<String> nicknames = new ArrayList<>();
        while(result.next()){
            nicknames.add(result.getString("nickname"));
        }
        return nicknames;
    }

    public ArrayList<String> discordIdsList() throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("SELECT discord FROM whitelist");
        ResultSet result = statement.executeQuery();
        ArrayList<String> discordIds = new ArrayList<>();
        while(result.next()){
            discordIds.add(result.getString("discord"));
        }
        return discordIds;
    }

    public String getNicknameByDiscordID(String discordID) throws SQLException{
        if(!checkDiscord(discordID)){ return "unknown"; }
        PreparedStatement statement = getConnection().prepareStatement("SELECT nickname FROM whitelist WHERE discord = ?");
        statement.setString(1, discordID);
        ResultSet result = statement.executeQuery();
        if(result.next()) { return result.getString("nickname"); }
        return "unknown";
    }
}
