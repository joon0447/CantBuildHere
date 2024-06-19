package org.joon.cannotbuildhere.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.joon.cannotbuildhere.CanNotBuildHere;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        for(Location l : CanNotBuildHere.coreLoc.keySet()){
            if(CanNotBuildHere.coreLoc.get(l).equals(uuid)){
                CanNotBuildHere.areaUUID.add(uuid);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        CanNotBuildHere.areaUUID.remove(uuid);
    }
}
