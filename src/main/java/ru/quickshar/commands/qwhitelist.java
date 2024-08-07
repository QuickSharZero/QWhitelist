package ru.quickshar.commands;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import ru.quickshar.QWhitelist;
import ru.quickshar.Util;
import ru.quickshar.abstr.AbstractCommands;

import java.sql.SQLException;
import java.util.List;

public class qwhitelist extends AbstractCommands {
    public qwhitelist() {
        super("qwhitelist");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            if(!sender.hasPermission("qwh.qwhitelist.reload")){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPerm"));
                return;
            }
            QWhitelist.getInstance().reloadConfig();
            sender.sendMessage(Util.getMessage("messages.qwhitelist.sucReload"));
            return;
        }
        // /qwhitelist remove <nickname>
        if(args[0].equalsIgnoreCase("remove")){
            if(!sender.hasPermission("qwh.qwhitelist.remove")){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPerm"));
                return;
            }
            if(args.length == 1){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.PlayerNotSpec"));
                return;
            }
            try{
                if(!QWhitelist.getInstance().getDatabase().checkPlayer(args[1])){
                    sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPlayer"));
                    return;
                }
                QWhitelist.getInstance().getDatabase().removePlayer(args[1]);
                sender.sendMessage(Util.getMessage("messages.qwhitelist.sucRemove"));
                return;
            }catch (SQLException ex){ ex.printStackTrace(); }
        }
        // /qwhitelist add <nickname>
        if(args[0].equalsIgnoreCase("add")){
            if(!sender.hasPermission("qwh.qwhitelist.add")){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPerm"));
                return;
            }
            if(args.length == 1){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.PlayerNotSpec"));
                return;
            }
            try{
                if(QWhitelist.getInstance().getDatabase().checkPlayer(args[1])){
                    sender.sendMessage(Util.getMessage("messages.ErrorMessages.PlayerAlready"));
                    return;
                }
                QWhitelist.getInstance().getDatabase().addPlayer(args[1], "null");
                sender.sendMessage(Util.getMessage("messages.qwhitelist.sucAdd"));
                return;
            }catch(SQLException ex) { ex.printStackTrace(); }
        }
        // /qwhitelist update <DiscordID>
        if(args[0].equalsIgnoreCase("update")){
            if(!sender.hasPermission("qwh.qwhitelist.update")){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPerm"));
                return;
            }
            if(args.length == 1){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.DiscordNotSpec"));
                return;
            }
            if(args.length == 2){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.NewNickNotSpec"));
                return;
            }
            try{
                if(!QWhitelist.getInstance().getDatabase().checkDiscord(args[1])){
                    sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoDiscord"));
                    return;
                }
                QWhitelist.getInstance().getDatabase().updateNickname(args[1], args[2]);
                sender.sendMessage(Util.getMessage("messages.qwhitelist.sucUpdate"));
            }catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args){
        try {
            if(args.length == 1) return Lists.newArrayList("remove", "add","update");
            if(args[0].equalsIgnoreCase("remove")) return QWhitelist.getInstance().getDatabase().nicknamesList();
            if(args[0].equalsIgnoreCase("add")) return Lists.newArrayList("<nickname>");
            if(args[0].equalsIgnoreCase("update")) return QWhitelist.getInstance().getDatabase().discordIdsList();
            if(args[0].equalsIgnoreCase("update") && args.length == 2) return  Lists.newArrayList("<NewNickName>");
        }catch (SQLException ex){ ex.printStackTrace(); }
        return Lists.newArrayList();
    }

}
