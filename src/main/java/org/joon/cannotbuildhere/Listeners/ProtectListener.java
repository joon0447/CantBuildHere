package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.joon.cannotbuildhere.CanNotBuildHere;

import java.util.UUID;

public class ProtectListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.getBlock().getType() == Material.SEA_LANTERN){
            Location loc = e.getBlock().getLocation();
            for(Location l : CanNotBuildHere.coreLoc.keySet()){
                if(l.equals(loc)){
                    Player player = e.getPlayer();
                    player.sendMessage(CanNotBuildHere.prefix + "코어는 파괴할 수 없습니다!");
                    e.setCancelled(true);
                }
            }
            return;
        }

        if(e.getBlock().getType() == Material.RED_STAINED_GLASS){
            Location loc = e.getBlock().getLocation();
            double lx = loc.getX();
            double ly = loc.getY();
            double lz = loc.getZ();

            double cx,cy,cz;
            for(Location l : CanNotBuildHere.coreLoc.keySet()){
                cx = l.getX();
                cy = l.getY();
                cz = l.getZ();
                if(cx-6 <= lx && lx <= cx+6){
                    if(cz-6 <= lz || lz <= cz+6){
                        if(cy-3<= ly && ly <= (cy-3) + CanNotBuildHere.getInstance().areaDefaultHeight){
                            e.setCancelled(true);
                            Player player = e.getPlayer();
                            player.sendMessage(CanNotBuildHere.prefix + "건차의 테두리는 파괴할 수 없습니다!");
                            return;
                        }
                    }
                }
            }
        }

//        Location pLoc = e.getBlock().getLocation();
//        int areaDefaultSize = CanNotBuildHere.getInstance().areaDefaultSize;
//        Player player = e.getPlayer();
//        for(Location l : CanNotBuildHere.coreLoc.keySet()){
//            double px = pLoc.getX();
//            double py = pLoc.getY();
//            double pz = pLoc.getZ();
//
//            double lx = l.getX();
//            double ly = l.getY();
//            double lz = l.getZ();
//            if(!player.getUniqueId().toString().equals(CanNotBuildHere.coreLoc.get(l))
//                    &&((Math.abs(px-lx)<=areaDefaultSize/2+1 && Math.abs(pz-lz)<=areaDefaultSize/2+1))
//            && (ly-3<=py && py<=ly+(CanNotBuildHere.getInstance().areaDefaultHeight-3))){
//                String owner = Bukkit.getOfflinePlayer(UUID.fromString(CanNotBuildHere.coreLoc.get(l))).getName();
//                player.sendMessage(CanNotBuildHere.prefix + owner + "님의 지역입니다.");
//                e.setCancelled(true);
//                return;
//            }
//        }
    }

//    @EventHandler
//    public void onPlace(BlockPlaceEvent e) {
//        Location pLoc = e.getBlock().getLocation();
//        int areaDefaultSize = CanNotBuildHere.getInstance().areaDefaultSize;
//        Player player = e.getPlayer();
//        for(Location l : CanNotBuildHere.coreLoc.keySet()){
//            double px = pLoc.getX();
//            double py = pLoc.getY();
//            double pz = pLoc.getZ();
//
//            double lx = l.getX();
//            double ly = l.getY();
//            double lz = l.getZ();
//            if(!player.getUniqueId().toString().equals(CanNotBuildHere.coreLoc.get(l))
//                    &&((Math.abs(px-lx)<=areaDefaultSize/2+1 && Math.abs(pz-lz)<=areaDefaultSize/2+1)
//                    && (ly-3<=py && py<=ly+(CanNotBuildHere.getInstance().areaDefaultHeight-3)))){
//                String owner = Bukkit.getOfflinePlayer(UUID.fromString(CanNotBuildHere.coreLoc.get(l))).getName();
//                player.sendMessage(CanNotBuildHere.prefix + owner + "님의 지역입니다.");
//                e.setCancelled(true);
//                return;
//            }
//        }
//    }

//    @EventHandler
//    public void onInteract(PlayerInteractEvent e) {
//        Block block = e.getClickedBlock();
//        if(block != null){
//            Location pLoc = block.getLocation();
//            if (e.getClickedBlock() != null &&
//                    (block.getType().toString().contains("CHEST") || block.getType() == Material.ANVIL
//                            || block.getType() == Material.BARREL || block.getType().toString().contains("SHULKER_BOX")
//                            || block.getType().toString().contains("FURNACE") || block.getType().toString().contains("DROPPER")
//                            || block.getType().toString().contains("DISPENSER"))) {
//                int areaDefaultSize = CanNotBuildHere.getInstance().areaDefaultSize;
//                Player player = e.getPlayer();
//                for (Location l : CanNotBuildHere.coreLoc.keySet()) {
//                    double px = pLoc.getX();
//                    double py = pLoc.getY();
//                    double pz = pLoc.getZ();
//
//                    double lx = l.getX();
//                    double ly = l.getY();
//                    double lz = l.getZ();
//                    if (!player.getUniqueId().toString().equals(CanNotBuildHere.coreLoc.get(l))
//                            && ((Math.abs(px - lx) <= areaDefaultSize / 2 + 1 && Math.abs(pz - lz) <= areaDefaultSize / 2 + 1)
//                            && (ly-3<=py && py<=ly+(CanNotBuildHere.getInstance().areaDefaultHeight-3)))) {
//                        String owner = Bukkit.getOfflinePlayer(UUID.fromString(CanNotBuildHere.coreLoc.get(l))).getName();
//                        player.sendMessage(CanNotBuildHere.prefix + owner + "님의 지역입니다.");
//                        e.setCancelled(true);
//                        return;
//                    }
//                }
//            }
//        }
//    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent e){
        Location eLoc = e.getBlock().getLocation();
        int areaDefaultSize = CanNotBuildHere.getInstance().areaDefaultSize;
        for(Location l : CanNotBuildHere.coreLoc.keySet()){
            double px = eLoc.getX();
            double pz = eLoc.getZ();
            double lx = l.getX();
            double lz = l.getZ();
            if(((Math.abs(px-lx)<=areaDefaultSize/2+3 && Math.abs(pz-lz)<=areaDefaultSize/2+3))){
                e.setCancelled(true);
                return;
            }
        }
    }
}
