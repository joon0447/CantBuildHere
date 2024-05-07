package org.joon.cannotbuildhere;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.joon.cannotbuildhere.Commands.BuildAdminCommand;
import org.joon.cannotbuildhere.Commands.TestCommand;
import org.joon.cannotbuildhere.Listeners.*;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Utils.LoadItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class CanNotBuildHere extends JavaPlugin {
    public static String prefix = ChatColor.RED + "[ ! ] " + ChatColor.RESET;
    public static ArrayList<String> adminList = new ArrayList<>();
    public static Map<Double[], Double[]> notAreaList = new HashMap<>(); // 마을 생성 금지 구역
    public int area;
    public int areaDefaultSize;
    public int areaDefaultHeight;
    public String areaWorld;

    public final LoadItem loadItem = new LoadItem();
    public static HashMap<Location, String> coreLoc;  // 건차 코어 저장

    private WorldGuardPlugin worldGuardPlugin;
    
    @Override
    public void onEnable() {
        worldGuardPlugin = getWorldGuard();

        areaWorld = this.getConfig().getString("건차 생성 월드");
        area = this.getConfig().getInt("건차 간의 간격");
        areaDefaultSize = this.getConfig().getInt("건차 기본 범위");
        areaDefaultHeight = this.getConfig().getInt("건차 기본 높이");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        DataManager dataManager = new DataManager();
        dataManager.createFile();
        dataManager.loadNotAdminArea();
        coreLoc = dataManager.loadCoreLocation();

        Bukkit.getPluginManager().registerEvents(new BuildCreateListener(loadItem), this);
        Bukkit.getPluginManager().registerEvents(new AdminAreaSetListener(loadItem), this);
        Bukkit.getPluginManager().registerEvents(new CoreClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectListener(), this);

        getCommand("test").setExecutor(new TestCommand(this)); // 테스트
        getCommand("건차").setExecutor(new BuildAdminCommand(loadItem));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public static CanNotBuildHere getInstance(){
        return JavaPlugin.getPlugin(CanNotBuildHere.class);
    }
}
