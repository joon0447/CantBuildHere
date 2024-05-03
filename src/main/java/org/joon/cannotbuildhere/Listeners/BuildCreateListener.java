package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Utils.BuildLine;
import org.joon.cannotbuildhere.Utils.LoadItem;

import java.io.IOException;

public class BuildCreateListener implements Listener {

    private final LoadItem loadItem;

    public BuildCreateListener(LoadItem loadItem) {
        this.loadItem = loadItem;
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(player.getInventory().getItemInMainHand().equals(loadItem.createPaper())){
                Location loc = player.getLocation();
                double x = Math.floor(loc.getX());
                double z = Math.floor(loc.getZ());
                if(!new DataManager().checkArea(x,z)){
                    player.sendMessage(CanNotBuildHere.prefix + "이곳은 생성 불가지역입니다!");
                }else{
                    if(new DataManager().checkAlreadyCreate(player.getName())){
                        World world = player.getWorld();
                        double y = Math.floor(loc.getY()) + 2; // 건차 중앙 블록 y좌표
                        Location blockLoc = new Location(world,x,y,z);
                        Block block = world.getBlockAt(blockLoc);
                        block.setType(Material.HONEY_BLOCK);  // 건차 중앙 관리 블럭
                        BuildLine buildLine = new BuildLine();
                        buildLine.BuildAreaLine(player); // 건차 라인 생성
                        new DataManager().saveArea(player.getName(), blockLoc, buildLine.locations);
                        player.sendMessage(CanNotBuildHere.prefix + "생성 가능지역.");
                    }else{
                        player.sendMessage(CanNotBuildHere.prefix + "이미 본인 소유 또는 소속된 건차가 존재합니다!");
                    }
                }
            }
        }
    }
}
