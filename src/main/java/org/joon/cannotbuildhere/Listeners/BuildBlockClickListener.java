package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BuildBlockClickListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType().equals(Material.HONEY_BLOCK)){
                Player player = e.getPlayer();
                player.sendMessage("꿀벌");
            }
        }
    }
}
