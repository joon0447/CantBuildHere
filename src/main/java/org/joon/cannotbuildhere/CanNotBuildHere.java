package org.joon.cannotbuildhere;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.joon.cannotbuildhere.Commands.BuildAdminCommand;
import org.joon.cannotbuildhere.Commands.TestCommand;
import org.joon.cannotbuildhere.Listeners.AdminAreaSetListener;
import org.joon.cannotbuildhere.Listeners.BuildBlockClickListener;
import org.joon.cannotbuildhere.Listeners.BuildCreateListener;
import org.joon.cannotbuildhere.Managers.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class CanNotBuildHere extends JavaPlugin {
    public static String prefix = ChatColor.RED + "[ ! ] " + ChatColor.RESET;
    public static ArrayList<String> adminList = new ArrayList<>();
    public static Map<Double[], Double[]> notAreaList = new HashMap<>(); // 마을 생성 금지 구역

    public int area = this.getConfig().getInt("건차 간의 간격");
    public int areaDefaultSize = this.getConfig().getInt("건차 생성 후 기본 범위");

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        DataManager dataManager = new DataManager();
        dataManager.createFile();
        dataManager.loadNotAdminArea();

        Bukkit.getPluginManager().registerEvents(new BuildCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new AdminAreaSetListener(), this);
        Bukkit.getPluginManager().registerEvents(new BuildBlockClickListener(), this);

        getCommand("test").setExecutor(new TestCommand()); // 테스트
        getCommand("건차").setExecutor(new BuildAdminCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CanNotBuildHere getInstance(){
        return JavaPlugin.getPlugin(CanNotBuildHere.class);
    }
}
