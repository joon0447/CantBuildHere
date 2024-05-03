package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.joon.cannotbuildhere.CanNotBuildHere;

public class ProtectCoreLineListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.getBlock().getType() == Material.HONEY_BLOCK){
            Location loc = e.getBlock().getLocation();
            for(Location l : CanNotBuildHere.coreLoc.keySet()){
                if(l.equals(loc)){
                    Player player = e.getPlayer();
                    player.sendMessage(CanNotBuildHere.prefix + "코어는 파괴할 수 없습니다!");
                    e.setCancelled(true);
                }
            }
        }

        if(e.getBlock().getType() == Material.RED_STAINED_GLASS){
            Location loc = e.getBlock().getLocation();
            for(Location l : CanNotBuildHere.lineAndCoreLoc){
                if(l.equals(loc)){
                    e.setCancelled(true);
                    Player player = e.getPlayer();
                    player.sendMessage(CanNotBuildHere.prefix + "건차의 테두리는 파괴할 수 없습니다!");
                }
            }
        }
    }
}
