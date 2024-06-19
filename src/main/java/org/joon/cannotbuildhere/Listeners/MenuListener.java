package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Menus.Menu;
import org.joon.cannotbuildhere.Utils.GetUUID;
import org.joon.cannotbuildhere.Utils.WorldGuardUtil;

import java.io.IOException;
import java.util.UUID;


public class MenuListener implements Listener {

    DataManager data = new DataManager();
    WorldGuardUtil wg = new WorldGuardUtil();
    GetUUID getUUID = new GetUUID();
    Menu menu = new Menu();

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) throws IOException {
        Player player;
        String uuid;
        if(e.getView().getTitle().contains("님의 지역")){
            e.setCancelled(true);
            player = (Player) e.getWhoClicked();
            uuid = player.getUniqueId().toString();
            switch(e.getSlot()){
                case 0:
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                    menu.inviteMenu(player, 1);
                    break;
                case 1:
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                    menu.removeUserMenu(player, 1);
                    break;
                case 8:
                    wg.removeProtectRegion(player.getUniqueId().toString(), player.getWorld());
                    data.removeArea(uuid, player);
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2, 1);
                    CanNotBuildHere.areaUUID.remove(player.getUniqueId().toString());
                    player.sendMessage(CanNotBuildHere.prefix + "건차 철거가 완료되었습니다.");
                    break;
                default:
                    break;
            }
        }else if(ChatColor.translateAlternateColorCodes
                ('&', e.getView().getTitle()).equals(ChatColor.BLUE + "권한 부여")
                && e.getCurrentItem()!=null){
            e.setCancelled(true);
            int page = Integer.parseInt(e.getInventory().getItem(48).getItemMeta().getLocalizedName());
            if (e.getRawSlot()==48 && e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)){
                menu.inviteMenu((Player) e.getWhoClicked(), page-1);
            }else if(e.getRawSlot() == 50 && e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)){
                menu.inviteMenu((Player) e.getWhoClicked(), page+1);
            }
            player = (Player) e.getWhoClicked();
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
            String target = getUUID.getUUID(e.getCurrentItem().getItemMeta().getDisplayName());
            if(target != null){
                Player pTarget = Bukkit.getPlayer(UUID.fromString(target));
                wg.addMember(player.getUniqueId().toString(), target, player.getWorld());
                player.sendMessage(CanNotBuildHere.prefix + e.getCurrentItem().getItemMeta().getDisplayName()
                        + " 님에게 권한을 부여했습니다.");
                player.closeInventory();
                if(pTarget != null){
                    pTarget.sendMessage(CanNotBuildHere.prefix + player.getName() + "님의 지역의 권한을 획득했습니다.");
                }
            }

        }else if(ChatColor.translateAlternateColorCodes
                ('&', e.getView().getTitle()).equals(ChatColor.BLUE + "권한 뺏기")
                && e.getCurrentItem()!=null){
            e.setCancelled(true);
            int page = Integer.parseInt(e.getInventory().getItem(48).getItemMeta().getLocalizedName());
            if (e.getRawSlot()==48 && e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)){
                menu.inviteMenu((Player) e.getWhoClicked(), page-1);
            }else if(e.getRawSlot() == 50 && e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)){
                menu.inviteMenu((Player) e.getWhoClicked(), page+1);
            }
            player = (Player) e.getWhoClicked();
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
            String target = getUUID.getUUID(e.getCurrentItem().getItemMeta().getDisplayName());
            if(target != null){
                Player pTarget = Bukkit.getPlayer(UUID.fromString(target));
                wg.removeMember(player.getUniqueId().toString(), target, player.getWorld());
                player.sendMessage(CanNotBuildHere.prefix + e.getCurrentItem().getItemMeta().getDisplayName()
                        + " 님의 권한을 삭제했습니다.");
                player.closeInventory();
                if(pTarget != null){
                    pTarget.sendMessage(CanNotBuildHere.prefix + player.getName() + "님의 지역의 권한이 삭제되었습니다.");
                }
            }
        }
    }
}
