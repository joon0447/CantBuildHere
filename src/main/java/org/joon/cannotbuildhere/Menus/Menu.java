package org.joon.cannotbuildhere.Menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.joon.cannotbuildhere.Utils.GetUUID;
import org.joon.cannotbuildhere.Utils.PageUtil;
import org.joon.cannotbuildhere.Utils.WorldGuardUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Menu {

    GetUUID getUUID = new GetUUID();
    WorldGuardUtil worldGuardUtil = new WorldGuardUtil();

    public void coreMenu(Player player){
        Inventory inv =
                Bukkit.createInventory(null, 9, ChatColor.BLUE + player.getName() + " 님의 지역");

        ItemStack coreRemove = new ItemStack(Material.BARRIER);
        ItemMeta coreRemoveMeta = coreRemove.getItemMeta();
        coreRemoveMeta.setDisplayName(ChatColor.RED + "건차 삭제");
        coreRemoveMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "건차는 돌려받을 수 없습니다!"));
        coreRemove.setItemMeta(coreRemoveMeta);
        inv.setItem(8, coreRemove);

        ItemStack invite = new ItemStack(Material.SLIME_BALL);
        ItemMeta inviteMeta = invite.getItemMeta();
        inviteMeta.setDisplayName(ChatColor.GREEN + "권한 부여하기");
        invite.setItemMeta(inviteMeta);
        inv.setItem(0, invite);

        ItemStack deport = new ItemStack(Material.REDSTONE);
        ItemMeta deportMeta = deport.getItemMeta();
        deportMeta.setDisplayName(ChatColor.RED + "권한 뺏기");
        deport.setItemMeta(deportMeta);
        inv.setItem(1, deport);
        player.openInventory(inv);
    }

    public void inviteMenu(Player player, int page) throws IOException {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "권한 부여");
        List<ItemStack> allItems = new ArrayList<ItemStack>();
        ItemStack line = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta lineMeta = line.getItemMeta();
        lineMeta.setDisplayName(ChatColor.GREEN + "온라인 유저만 표시됩니다.");
        line.setItemMeta(lineMeta);

        for(int i=0; i<9; i++){
            inv.setItem(i, line);
        }
        for(int i=9; i<=45; i+=9){
            inv.setItem(i, line);
        }
        for(int i=17; i<=53; i+=9){
            inv.setItem(i, line);
        }
        for(int i=46; i<=52; i++){
            if (i == 48 || i == 50) {
                continue;
            }
            inv.setItem(i, line);
        }

        ItemStack playerHead;
        SkullMeta playerHeadMeta;
        String pUUID;
        for(Player p : Bukkit.getOnlinePlayers()){
            pUUID = getUUID.getUUID(p.getName());
            if(!p.equals(player) && worldGuardUtil.findJoinMember(player.getUniqueId().toString(), pUUID, player.getWorld())){
                playerHead = new ItemStack(Material.PLAYER_HEAD);
                playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                playerHeadMeta.setOwningPlayer(p);
                playerHeadMeta.setDisplayName(p.getName());
                playerHeadMeta.setLore(Collections.singletonList(ChatColor.GREEN + "좌클릭시 지역의 권한을 부여합니다."));
                playerHead.setItemMeta(playerHeadMeta);
                allItems.add(playerHead);
            }
        }

        ItemStack left;
        ItemMeta leftMeta;
        if(PageUtil.isPageValid(allItems, page-1, 28)){
            left = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            leftMeta = left.getItemMeta();
            assert leftMeta != null;
            leftMeta.setDisplayName(ChatColor.GREEN + "이전 페이지");
        }else{
            left = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            leftMeta = left.getItemMeta();
            assert leftMeta != null;
            leftMeta.setDisplayName(ChatColor.RED + "이전 페이지가 없습니다.");
        }
        leftMeta.setLocalizedName(page + "");
        left.setItemMeta(leftMeta);
        inv.setItem(48, left);

        ItemStack right;
        ItemMeta rightMeta;
        if(PageUtil.isPageValid(allItems, page+1, 28)){
            right = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            rightMeta = right.getItemMeta();
            assert rightMeta != null;
            rightMeta.setDisplayName(ChatColor.GREEN + "다음 페이지");
        }else{
            right = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            rightMeta = right.getItemMeta();
            assert rightMeta != null;
            rightMeta.setDisplayName(ChatColor.RED + "다음 페이지가 없습니다.");
        }
        right.setItemMeta(rightMeta);
        inv.setItem(50, right);

        for(ItemStack is : PageUtil.getPageItems(allItems, page, 28)){
            inv.setItem(inv.firstEmpty(), is);
        }

        player.openInventory(inv);
    }

    public void removeUserMenu(Player player, int page) throws IOException {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "권한 뺏기");
        List<ItemStack> allItems = new ArrayList<ItemStack>();
        ItemStack line = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta lineMeta = line.getItemMeta();
        lineMeta.setDisplayName(ChatColor.GREEN + "온라인 유저만 표시됩니다.");
        line.setItemMeta(lineMeta);

        for(int i=0; i<9; i++){
            inv.setItem(i, line);
        }
        for(int i=9; i<=45; i+=9){
            inv.setItem(i, line);
        }
        for(int i=17; i<=53; i+=9){
            inv.setItem(i, line);
        }
        for(int i=46; i<=52; i++){
            if (i == 48 || i == 50) {
                continue;
            }
            inv.setItem(i, line);
        }

        ItemStack playerHead;
        SkullMeta playerHeadMeta;
        String pUUID;
        for(Player p : Bukkit.getOnlinePlayers()){
            pUUID = getUUID.getUUID(p.getName());
            if(!p.equals(player) && !worldGuardUtil.findJoinMember(player.getUniqueId().toString(), pUUID, player.getWorld())){
                playerHead = new ItemStack(Material.PLAYER_HEAD);
                playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                playerHeadMeta.setOwningPlayer(p);
                playerHeadMeta.setDisplayName(p.getName());
                playerHeadMeta.setLore(Collections.singletonList(ChatColor.RED + "좌클릭시 지역의 권한을 삭제합니다."));
                playerHead.setItemMeta(playerHeadMeta);
                allItems.add(playerHead);
            }
        }

        ItemStack left;
        ItemMeta leftMeta;
        if(PageUtil.isPageValid(allItems, page-1, 28)){
            left = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            leftMeta = left.getItemMeta();
            assert leftMeta != null;
            leftMeta.setDisplayName(ChatColor.GREEN + "이전 페이지");
        }else{
            left = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            leftMeta = left.getItemMeta();
            assert leftMeta != null;
            leftMeta.setDisplayName(ChatColor.RED + "이전 페이지가 없습니다.");
        }
        leftMeta.setLocalizedName(page + "");
        left.setItemMeta(leftMeta);
        inv.setItem(48, left);

        ItemStack right;
        ItemMeta rightMeta;
        if(PageUtil.isPageValid(allItems, page+1, 28)){
            right = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            rightMeta = right.getItemMeta();
            assert rightMeta != null;
            rightMeta.setDisplayName(ChatColor.GREEN + "다음 페이지");
        }else{
            right = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            rightMeta = right.getItemMeta();
            assert rightMeta != null;
            rightMeta.setDisplayName(ChatColor.RED + "다음 페이지가 없습니다.");
        }
        right.setItemMeta(rightMeta);
        inv.setItem(50, right);

        for(ItemStack is : PageUtil.getPageItems(allItems, page, 28)){
            inv.setItem(inv.firstEmpty(), is);
        }

        player.openInventory(inv);
    }

}
