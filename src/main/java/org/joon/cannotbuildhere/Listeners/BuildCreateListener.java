package org.joon.cannotbuildhere.Listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Utils.BuildLine;
import org.joon.cannotbuildhere.Utils.LoadItem;
import org.joon.cannotbuildhere.Utils.WorldGuardUtil;

import java.io.IOException;

public class BuildCreateListener implements Listener {

    private final LoadItem loadItem;
    private final WorldGuardUtil worldGuard = new WorldGuardUtil();
    private final DataManager dataManager;
    public BuildCreateListener(LoadItem loadItem, DataManager dataManager) {
        this.loadItem = loadItem;
        this.dataManager = dataManager;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        if(e.getClickedBlock() != null && e.getHand() == EquipmentSlot.HAND){
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK && player.getInventory().getItemInMainHand().equals(loadItem.createPaper())) {
                Location loc = e.getClickedBlock().getLocation();
                double x = Math.floor(loc.getX());
                double z = Math.floor(loc.getZ());
                if(!new DataManager().checkArea(x,z) || !player.getWorld().getName().equals(CanNotBuildHere.getInstance().areaWorld)){
                    player.sendMessage(CanNotBuildHere.prefix + "이곳은 생성 불가 지역입니다 !");
                }else{
                    int area = CanNotBuildHere.getInstance().area;
                    for(Location c : CanNotBuildHere.coreLoc.keySet()){
                        double cx = c.getX();
                        double cz = c.getZ();
                        if(Math.abs(cx-x)<=area && Math.abs(cz-z)<=area){
                            player.sendMessage(CanNotBuildHere.prefix + "주변에 다른 건차가 존재합니다 !");
                            return;
                        }
                    }
                    if(!CanNotBuildHere.areaUUID.contains(player.getUniqueId().toString())){
                        World world = player.getWorld();
                        double y = Math.floor(loc.getY()) + 3; // 건차 중앙 블록 y좌표
                        Location blockLoc = new Location(world,x,y,z);
                        int areaSize = CanNotBuildHere.getInstance().areaDefaultSize;
                        int areaHeight = CanNotBuildHere.getInstance().areaDefaultHeight;
                        Location p1 = new Location(world,x+(areaSize/2+1),y-3,z+(areaSize/2+1));
                        Location p2 = new Location(world,x-(areaSize/2+1),y+areaHeight,z-(areaSize/2+1));
                        Block block = world.getBlockAt(blockLoc);
                        block.setType(Material.SEA_LANTERN);  // 건차 중앙 관리 블럭
                        BuildLine buildLine = new BuildLine();
                        buildLine.BuildAreaLine(loc); // 건차 라인 생성
                        new DataManager().saveArea(player.getName(), blockLoc);
                        worldGuard.createProtectedRegion(player.getUniqueId().toString(), p1, p2);
                        worldGuard.addMember(player.getUniqueId().toString(), player.getUniqueId().toString(), player.getWorld());
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
                        player.getInventory().removeItem(loadItem.createPaper());
                        CanNotBuildHere.areaUUID.add(player.getUniqueId().toString());
                        player.sendMessage(CanNotBuildHere.prefix + "건설차단 지역을 생성했습니다 !");
                        player.sendMessage(CanNotBuildHere.prefix + "빨간 테두리로 구성된 면까지 보호를 받습니다.");
                    }else{
                        player.sendMessage(CanNotBuildHere.prefix + "이미 본인 소유의 건차가 존재합니다 !");
                    }
                }
            }
        }
    }

}

