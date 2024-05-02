package org.joon.cannotbuildhere.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;
import org.joon.cannotbuildhere.CanNotBuildHere;
import org.joon.cannotbuildhere.Listeners.AdminAreaSetListener;
import org.joon.cannotbuildhere.Managers.DataManager;
import org.joon.cannotbuildhere.Utils.GetUUID;
import org.joon.cannotbuildhere.Utils.LoadItem;

import java.io.IOException;
import java.util.Arrays;

public class BuildAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.isOp()){
                if(args.length == 1) {
                    if(args[0].equals("지급")){
                        player.getInventory().addItem(new LoadItem().createPaper());
                        player.sendMessage(CanNotBuildHere.prefix + "건설차단권이 지급되었습니다.");
                    }else if(args[0].equals("막대")){
                        player.getInventory().addItem(new LoadItem().setStick());
                        player.sendMessage(CanNotBuildHere.prefix + "건차금지구역 설정 막대가 지급되었습니다.");
                        player.sendMessage(CanNotBuildHere.prefix + "좌클릭, 우클릭으로 금지구역을 설정할 수 있습니다.");
                    }else if(args[0].equals("설정")){
                        try {
                            String uuid = new GetUUID().getUUID(player.getName());
                            CanNotBuildHere.adminList.add(uuid);
                            player.sendMessage(CanNotBuildHere.prefix + "건차금지구역 설정을 시작합니다.");
                            player.sendMessage(CanNotBuildHere.prefix + "좌클릭, 우클릭으로 구역을 설정해주세요.");
                            player.sendMessage(CanNotBuildHere.prefix + "구역은 좌클릭, 우클릭 2군데를 꼭짓점으로 정육면체로 설정됩니다.");
                            player.sendMessage(CanNotBuildHere.prefix + "두 개의 지점을 설정했으면 /건차 완료 를 입력해주세요.");
                        } catch (IOException e) {
                            player.sendMessage(CanNotBuildHere.prefix + "건차금지구역 설정모드 진입에 실패했습니다.");
                        }
                    }else{
                        player.sendMessage(CanNotBuildHere.prefix + "/건차 지급 : 건차생성권을 지급합니다.");
                        player.sendMessage(CanNotBuildHere.prefix + "/건차 막대 : 건차설정막대를 지급합니다.");
                        player.sendMessage(CanNotBuildHere.prefix + "/건차 설정 : 건차생성금지구역을 설정 모드에 진입합니다.");
                        player.sendMessage(CanNotBuildHere.prefix + "/건차 완료 지역이름 : 건차생성금지구역을 생성합니다.");
                    }
                }else if(args.length==2){
                    if(args[0].equals("완료")){
                        Location[] loc = AdminAreaSetListener.privateAreaLoc;
                        if(loc[0]==null || loc[1]==null){
                            player.sendMessage(CanNotBuildHere.prefix + "좌표가 제대로 설정되지 않았습니다!");
                            player.sendMessage(CanNotBuildHere.prefix + "좌클릭으로 좌표1, 우클릭으로 좌표2를 설정해주세요!");
                            return false;
                        }
                        String name = args[1];
                        new DataManager().saveNotAdminArea(name, loc);
                        try {
                            String uuid = new GetUUID().getUUID(player.getName());
                            CanNotBuildHere.adminList.remove(uuid);
                            player.sendMessage(CanNotBuildHere.prefix + name + " 지역의 건차금지구역이 설정되었습니다.");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else if(args[0].equals("설정")){
                        if(args[1].equals("취소")){
                            String uuid = null;
                            try {
                                uuid = new GetUUID().getUUID(player.getName());
                                CanNotBuildHere.adminList.remove(uuid);
                                player.sendMessage(CanNotBuildHere.prefix + "건차금지구역 설정모드를 종료했습니다.");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }else{
                    player.sendMessage(CanNotBuildHere.prefix + "/건차 지급 : 건차생성권을 지급합니다.");
                    player.sendMessage(CanNotBuildHere.prefix + "/건차 막대 : 건차설정막대를 지급합니다.");
                    player.sendMessage(CanNotBuildHere.prefix + "/건차 설정 : 건차생성금지구역을 설정 모드에 진입합니다.");
                    player.sendMessage(CanNotBuildHere.prefix + "/건차 완료 지역이름 : 건차생성금지구역을 생성합니다.");
                }
            }else{
                player.sendMessage(CanNotBuildHere.prefix + "이 명령어는 관리자만 사용 가능합니다!");
            }
        }
        return false;
    }
}
