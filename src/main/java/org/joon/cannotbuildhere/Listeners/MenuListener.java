package org.joon.cannotbuildhere.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Menus.AddMenu;
import org.joon.cannotbuildhere.Utils.WorldGuardUtil;


public class MenuListener implements Listener {

    DataManager data = new DataManager();
    WorldGuardUtil wg = new WorldGuardUtil();
    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player player;
        String uuid;
        if(e.getView().getTitle().contains("님의 지역")){
            e.setCancelled(true);
            player = (Player) e.getWhoClicked();
            uuid = player.getUniqueId().toString();
            switch(e.getSlot()){
                case 0:
                    break;
                case 8:
                    data.removeArea(uuid, player);
                    player.closeInventory();
                    wg.removeProtectRegion(player.getUniqueId().toString(), player.getWorld());
                    player.sendMessage(CanNotBuildHere.prefix + "건차 철거가 완료되었습니다.");
                    break;
                default:
                    break;
            }
        }
    }
}
