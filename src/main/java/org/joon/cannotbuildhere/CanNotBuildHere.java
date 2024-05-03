package org.joon.cannotbuildhere;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.joon.cannotbuildhere.Commands.BuildAdminCommand;
import org.joon.cannotbuildhere.Commands.TestCommand;
import org.joon.cannotbuildhere.Listeners.*;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Utils.LoadItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CanNotBuildHere extends JavaPlugin {
    public static String prefix = ChatColor.RED + "[ ! ] " + ChatColor.RESET;
    public static ArrayList<String> adminList = new ArrayList<>();
    public static Map<Double[], Double[]> notAreaList = new HashMap<>(); // 마을 생성 금지 구역
    public int area;
    public int areaDefaultSize;

    public final LoadItem loadItem = new LoadItem();
    public static HashMap<Location, String> coreLoc;  // 건차 코어 저장
    public static List<Location> lineAndCoreLoc;

    @Override
    public void onEnable() {
        area = this.getConfig().getInt("건차 간의 간격");
        areaDefaultSize = this.getConfig().getInt("건차 생성 후 기본 범위");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        DataManager dataManager = new DataManager();
        dataManager.createFile();
        dataManager.loadNotAdminArea();
        coreLoc = dataManager.loadCoreLocation();
        lineAndCoreLoc = dataManager.loadLineLocation();

        Bukkit.getPluginManager().registerEvents(new BuildCreateListener(loadItem), this);
        Bukkit.getPluginManager().registerEvents(new AdminAreaSetListener(loadItem), this);
        Bukkit.getPluginManager().registerEvents(new CoreClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectCoreLineListener(), this);

        getCommand("test").setExecutor(new TestCommand(this)); // 테스트
        getCommand("건차").setExecutor(new BuildAdminCommand(loadItem));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CanNotBuildHere getInstance(){
        return JavaPlugin.getPlugin(CanNotBuildHere.class);
    }
}
