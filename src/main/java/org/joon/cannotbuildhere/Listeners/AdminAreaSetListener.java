package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Utils.LoadItem;

public class AdminAreaSetListener implements Listener {

    public static Location[] privateAreaLoc = new Location[2];
    public final LoadItem loadItem;

    public AdminAreaSetListener(LoadItem loadItem) {
        this.loadItem = loadItem;
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(CanNotBuildHere.adminList.contains(player.getUniqueId().toString())) {
            if (e.getClickedBlock() != null && e.getHand() == EquipmentSlot.HAND) {
                if (player.isOp() && player.getInventory().getItemInMainHand().equals(loadItem.setStick())) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        e.setCancelled(true);
                        privateAreaLoc[0] = e.getClickedBlock().getLocation();
                        player.sendMessage(CanNotBuildHere.prefix + "좌표1을 설정하셨습니다!");
                    } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        privateAreaLoc[1] = e.getClickedBlock().getLocation();
                        e.setCancelled(true);
                        player.sendMessage(CanNotBuildHere.prefix + "좌표2를 설정하셨습니다!");
                    }
                }
            }
        }
    }
}
