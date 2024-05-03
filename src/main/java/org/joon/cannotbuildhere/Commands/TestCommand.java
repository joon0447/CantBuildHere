package org.joon.cannotbuildhere.Commands;

import org.bukkit.Location;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCommand implements CommandExecutor {
    private CanNotBuildHere plugin;

    public TestCommand(CanNotBuildHere plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            for(Location st : CanNotBuildHere.lineAndCoreLoc){
                player.sendMessage(st.toString());
            }
        }

        return false;
    }
}
