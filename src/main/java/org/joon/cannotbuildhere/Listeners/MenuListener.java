package org.joon.cannotbuildhere.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
//        if(ChatColor.translateAlternateColorCodes('&',
//                e.getView().getTitle()).equals(ChatColor.BLUE + "친구 메뉴")
//                && e.getCurrentItem()!=null){
//
//        }
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().contains("님의 지역")){
            e.setCancelled(true);
        }
    }
}
