package org.joon.cannotbuildhere.Managers;

import jdk.javadoc.internal.tool.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
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
        File folder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
        int id;
        if(folder.listFiles() == null){
            id = 1;
        }else{
            id = folder.listFiles().length;
        }
        File file = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList/" + id + ".yml");
        YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
        List<String> playerList = new ArrayList<>();
        playerList.add(playerUUID);
        yc.set("player", playerList);
        yc.set("centerX", loc.getX());
        yc.set("centerY", loc.getY());
        yc.set("centerZ", loc.getZ());
        yc.save(file);
    }

    public boolean checkAlreadyCreate(String name) throws IOException { // 본인 소유의 건차가 이미 있는지 확인
        File folder = new File(CanNotBuildHere.getInstance().getDataFolder(), "AreaList");
        String playerUUID = new GetUUID().getUUID(name);
        File[] files = folder.listFiles();
        if (files != null){
            for(File f : files){
                YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
                List<String> list = (List<String>) yc.get("player");
                for(String s : list){
                    if(s.equals(playerUUID)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void createPlayerFile(UUID uuid) {
        File file = new File(CanNotBuildHere.getInstance().getDataFolder(), "PlayerList/" + uuid+ ".yml");
        if(!file.exists()) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            List<String> friendsList = new ArrayList<String>();
            List<String> mailList = new ArrayList<String>();
            List<ItemStack> giftList = new ArrayList<ItemStack>();
            yml.set("player", uuid.toString());
            yml.set("friends", friendsList);
            yml.set("mail", mailList);
            yml.set("gift", giftList);
            try{
                yml.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public File loadPlayerFile(UUID uuid){
        return new File(CanNotBuildHere.getInstance().getDataFolder(), "PlayerList/" + uuid+ ".yml");
    }

}
