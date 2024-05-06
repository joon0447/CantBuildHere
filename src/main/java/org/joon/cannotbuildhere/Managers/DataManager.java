package org.joon.cannotbuildhere.Managers;

import jdk.javadoc.internal.tool.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Utils.GetUUID;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {

    public File PlayerListFolder;
    int areaDiff = CanNotBuildHere.getInstance().area;

    public void createFile() {
        PlayerListFolder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
        if(!PlayerListFolder.exists()) {
            PlayerListFolder.mkdirs();
        }
    }

    public boolean checkArea(double x, double z){
        Map<Double[], Double[]> map = CanNotBuildHere.notAreaList;
        for(Double[] d: map.keySet()){
            double x1 = d[0];
            double z1 = d[1];
            double x2 = map.get(d)[0];
            double z2 = map.get(d)[1];
            double tmp = 0;
            if(x1 > x2){
                tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            if(z1 > z2){
                tmp = z1;
                z1 = z2;
                z2 = tmp;
            }
            if(x1- areaDiff <= x && x2+ areaDiff >= x
                    && z1-areaDiff <= z && z2+areaDiff >= z){
                return false;
            }
        }

        return true;
    }

    public void loadNotAdminArea(){
        File file = new File(CanNotBuildHere.getInstance().getDataFolder(), "AdminAreaList");
        File[] files = file.listFiles();
        Double[] arr;
        Double[] arr2;
        if(files != null){
            for(File f : files){
                arr = new Double[2];
                arr2 = new Double[2];
                YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
                double x1 = yc.getDouble("locX1");
                double z1 = yc.getDouble("locZ1");
                arr[0] = x1;
                arr[1] = z1;
                double x2 = yc.getDouble("locX2");
                double z2 = yc.getDouble("locZ2");
                arr2[0] = x2;
                arr2[1] = z2;
                CanNotBuildHere.notAreaList.put(arr,arr2);
            }
        }
    }

    public void saveNotAdminArea(String name, Location[] loc){
        File file = new File(CanNotBuildHere.getInstance().getDataFolder(), "AdminAreaList/" + name + ".yml");
        YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
        yc.set("locX1", loc[0].getX());
        yc.set("locZ1", loc[0].getZ());
        yc.set("locX2", loc[1].getX());
        yc.set("locZ2", loc[1].getZ());
        try{
            yc.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Double[] arr;
        Double[] arr2;
        arr = new Double[2];
        arr2 = new Double[2];
        double x1 = loc[0].getX();
        double z1 = loc[0].getZ();
        arr[0] = x1;
        arr[1] = z1;
        double x2 = loc[1].getX();
        double z2 = loc[1].getZ();
        arr2[0] = x2;
        arr2[1] = z2;
        CanNotBuildHere.notAreaList.put(arr,arr2);
    }

    public void saveArea(String name, Location loc) throws IOException { // 건차 생성 시 데이터 저장
        String playerUUID = new GetUUID().getUUID(name);
        File file = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList/" + playerUUID + ".yml");
        YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
        yc.set("player", playerUUID);
        yc.set("upgrade", 1);
        Location core = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        yc.set("Core Location", core);
        CanNotBuildHere.coreLoc.put(core, playerUUID);
        yc.save(file);
    }

    public void destroyBlock(Location location) {
        if (location != null) {
            Block block = location.getBlock();
            if (block.getType() == Material.RED_STAINED_GLASS ||
            block.getType() == Material.HONEY_BLOCK) {
                block.setType(Material.AIR);
            }
        }
    }
    public void removeArea(String uuid, Player player){
        File file = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList/" + uuid + ".yml");
        YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
        Location cLoc = yc.getLocation("Core Location");
        Location loc;
        if(cLoc != null){
            World world = player.getWorld();
            double x = cLoc.getX();
            double y = cLoc.getY();
            double z = cLoc.getZ();
            for(double i=x-6; i<=x+6; i++){
                for(double k=y-3; k<=y+CanNotBuildHere.getInstance().areaDefaultHeight; k++){
                    for(double j=z-6; j<=z+6; j++){
                        loc = new Location(world, i, k, j);
                        destroyBlock(loc);
                    }
                }
            }
        }

        if(file.exists()){
            file.delete();
        }
        for(Location c : CanNotBuildHere.coreLoc.keySet()){
            if(CanNotBuildHere.coreLoc.get(c).equals(uuid)){
                CanNotBuildHere.coreLoc.remove(c);
            }
        }
    }


    public HashMap<Location, String> loadCoreLocation(){
        HashMap<Location, String> map = new HashMap<>();
        File folder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
        File[] files = folder.listFiles();
        YamlConfiguration yc;
        String playerUUID;
        Location coreLoc;
        if(files != null){
            for(File f : files){
                yc = YamlConfiguration.loadConfiguration(f);
                coreLoc = yc.getLocation("Core Location");
                playerUUID = yc.getString("player");
                map.put(coreLoc, playerUUID);
            }
        }
        System.out.println("건차 코어 좌표 로드에 성공했습니다.");
        return map;
    }

//    public List<Location> loadLineAndCoreLoc(){
//        File folder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
//        File[] files = folder.listFiles();
//        YamlConfiguration yc;
//        List<Location> list = new ArrayList<>();
//        if(files != null){
//            for(File f : files){
//                yc = YamlConfiguration.loadConfiguration(f);
//                List<Location> l = (List<Location>) yc.getList("Line Location");
//            }
//        }
//    }


    public boolean checkAlreadyCreate(String name) throws IOException { // 본인 소유의 건차가 이미 있는지 확인
        File folder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
        String playerUUID = new GetUUID().getUUID(name);
        File[] files = folder.listFiles();
        if (files != null){
            for(File f : files){
                YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
                String player = yc.getString("player");
                if(player.equals(playerUUID)){
                    return false;
                }
            }
        }
        return true;
    }
}
