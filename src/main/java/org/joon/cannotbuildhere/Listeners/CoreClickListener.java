package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Utils.GetUUID;
import org.joon.cannotbuildhere.Menus.Menu;

import java.io.IOException;

public class CoreClickListener implements Listener {

    private final Menu menu = new Menu();

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) throws IOException {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType().equals(Material.HONEY_BLOCK)){
                boolean check = false;
                Player player = e.getPlayer();
                String uuid = new GetUUID().getUUID(player.getName());
                Location eLoc = e.getClickedBlock().getLocation();
                double x1,x2,y1,y2,z1,z2;
                System.out.println("dsds");
                for(Location coreLc : CanNotBuildHere.coreLoc.keySet()){
                    x1 = eLoc.getX();
                    y1 = eLoc.getY();
                    z1 = eLoc.getZ();
                    x2 = coreLc.getX();
                    y2 = coreLc.getY();
                    z2 = coreLc.getZ();
                    System.out.println("x1 :" + x1);
                    System.out.println("x2 :" + x2);
                    System.out.println("z1 :" + z1);
                    System.out.println("z2 :" + z2);
                    System.out.println("y1 :" + y1);
                    System.out.println("y2 :" + y2);
                    System.out.println(uuid);
                    System.out.println(CanNotBuildHere.coreLoc.get(coreLc));
                    if(locCheck(x1,y1,z1,x2,y2,z2) && CanNotBuildHere.coreLoc.get(coreLc).equals(uuid)){
                        check = true;
                        break;
                    }
                }
                if(check){
                    menu.coreMenu(player);
                }
            }
        }

    }

    public boolean locCheck(double x1, double y1, double z1, double x2, double y2, double z2){
        if(Math.round(x1) == Math.round(x2) && Math.round(y1) == Math.round(y2) && Math.round(z1) == Math.round(z2)){
            return true;
        }
        return false;
    }
}
