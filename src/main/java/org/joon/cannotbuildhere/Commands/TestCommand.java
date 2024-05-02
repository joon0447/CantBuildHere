package org.joon.cannotbuildhere.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Utils.GetUUID;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            File folder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
            try {
                String playerUUID = new GetUUID().getUUID(player.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            File[] files = folder.listFiles();
            if (files != null){
                for(File f : files){
                    YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
                    List<String> list = (List<String>) yc.getList("player");
                    for(String ts : list){
                        player.sendMessage(ts);
                    }
                }
            }
        }

        return false;
    }
}
