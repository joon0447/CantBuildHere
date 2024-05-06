package org.joon.cannotbuildhere.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LoadItem {

    public ItemStack createPaper(){
        ItemStack buildItem = new ItemStack(Material.COPPER_INGOT);
        ItemMeta buildItemMeta = buildItem.getItemMeta();
        buildItemMeta.setDisplayName(ChatColor.GREEN + "건설차단권");
        buildItemMeta.setLore(Arrays.asList(ChatColor.BLUE + "우클릭으로 사용 가능합니다", ChatColor.BLUE +"지옥, 엔더에서는 사용이 불가능합니다."));
        buildItem.setItemMeta(buildItemMeta);
        return buildItem;
    }

    public ItemStack setStick(){
        ItemStack setItem = new ItemStack(Material.BLAZE_ROD);
        ItemMeta setItemMeta = setItem.getItemMeta();
        setItemMeta.setDisplayName(ChatColor.RED + "건차금지구역 설정 막대");
        setItem.setItemMeta(setItemMeta);
        return setItem;
    }
}
