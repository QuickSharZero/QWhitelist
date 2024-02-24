package ru.quickshar.commands;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import ru.quickshar.QWhitelist;
import ru.quickshar.Util;
import ru.quickshar.abstr.AbstractCommands;

import java.sql.SQLException;
import java.util.List;

public class getCodeCommand extends AbstractCommands {
    public getCodeCommand() {
        super("code");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            try {
                if(!QWhitelist.getInstance().getDatabase().checkPlayer(sender.getName())){
                    sender.sendMessage(Util.getMessage("messages.ErrorMessages.NotWhitelist"));
                    return;
                }
                sender.sendMessage(Util.getMessage("messages.code.sucOwnCode") + " " + QWhitelist.getInstance().getDatabase().getPlayerCode(sender.getName()));
            }catch (SQLException ex){ ex.printStackTrace(); }
            return;
        }

        if(args.length == 1){
            if(!sender.hasPermission("qwh.code.admin")){
                sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPerm"));
                return;
            }
            try {
                if (!QWhitelist.getInstance().getDatabase().checkPlayer(args[0])){
                    sender.sendMessage(Util.getMessage("messages.ErrorMessages.NoPlayer"));
                    return;
                }
            }catch (SQLException ex){ex.printStackTrace();}
            try{
                String message = Util.getMessage("messages.code.sucOtherCode");
                if(message.contains("%p")){
                    message.replace("%p", "" + args[0].toString());
                    sender.sendMessage(message.replace("%p", args[0]) + QWhitelist.getInstance().getDatabase().getPlayerCode(args[0]));
                    return;
                }
                sender.sendMessage(message + QWhitelist.getInstance().getDatabase().getPlayerCode(args[0]));
                return;
            }catch (SQLException ex){ ex.printStackTrace();}
        }

    }

    @Override
    public List<String> complete(CommandSender sender, String[] args){
        try {
            if(args.length == 1 && sender.hasPermission("qwh.code.admin")){ return QWhitelist.getInstance().getDatabase().nicknamesList(); }
        }catch (SQLException ex){ ex.printStackTrace(); }
        return Lists.newArrayList();
    }
}
