package org.joon.cannotbuildhere.Utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joon.cannotbuildhere.CanNotBuildHere;

import java.util.Set;
import java.util.UUID;

public class WorldGuardUtil {

    public void createProtectedRegion(String regionName, Location pos1, Location pos2) {
        World world = pos1.getWorld();
        Player player = Bukkit.getPlayer(UUID.fromString(regionName));
        BlockVector3 pt1 = BlockVector3.at(pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ());
        BlockVector3 pt2 = BlockVector3.at(pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ());

        ProtectedRegion region = new ProtectedCuboidRegion(regionName, pt1, pt2);
        region.setFlag(Flags.BUILD.getRegionGroupFlag(), RegionGroup.MEMBERS);
        region.setFlag(Flags.PVP, StateFlag.State.DENY);
        region.setFlag(Flags.LIGHTNING, StateFlag.State.DENY);
        region.setFlag(Flags.DENY_MESSAGE, CanNotBuildHere.prefix + player.getName() +"님의 지역입니다.");
        com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world)).addRegion(region);

    }

    public void regionSetPVP(String owner, World world){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));
        if(regions != null) {
            ProtectedRegion region = regions.getRegion(owner);
            if(region.getFlag(Flags.PVP) == StateFlag.State.DENY){
                region.setFlag(Flags.PVP, StateFlag.State.ALLOW);
            }else{
                region.setFlag(Flags.PVP, StateFlag.State.DENY);
            }
        }
    }

    public void removeProtectRegion(String regionName, World world){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));
        regions.removeRegion(regionName);
    }

    public void addMember(String owner, String target, World world){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));
        if(regions != null){
            ProtectedRegion region = regions.getRegion(owner);
            DefaultDomain members = region.getMembers();
            members.addPlayer(UUID.fromString(target));
            members.addGroup("members");
            region.setMembers(members);
        }
    }

    public void removeMember(String owner, String target, World world){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));
        if(regions != null){
            ProtectedRegion region = regions.getRegion(owner);
            DefaultDomain members = region.getMembers();
            members.removePlayer(UUID.fromString(target));
            region.setMembers(members);
        }
    }

    public boolean findJoinMember(String owner, String target, World world){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(world));
        if(regions != null){
            ProtectedRegion region = regions.getRegion(owner);
            DefaultDomain members = region.getMembers();
            if(members.contains(UUID.fromString(target))){
                return false;
            }
        }
        return true;
    }
}
