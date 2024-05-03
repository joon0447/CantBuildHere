package org.joon.cannotbuildhere.Menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Menu {

    public void coreMenu(Player player){
        Inventory inv =
                Bukkit.createInventory(null, 9, ChatColor.BLUE + player.getName() + " 님의 지역");

        ItemStack coreRemove = new ItemStack(Material.BARRIER);
        ItemMeta coreRemoveMeta = coreRemove.getItemMeta();
        coreRemoveMeta.setDisplayName(ChatColor.RED + "건차 삭제");
        coreRemoveMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "건차는 돌려받을 수 없습니다!"));
        coreRemove.setItemMeta(coreRemoveMeta);
        inv.setItem(8, coreRemove);
        player.openInventory(inv);
    }
}
