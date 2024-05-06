package org.joon.cannotbuildhere.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.joon.cannotbuildhere.CanNotBuildHere;

import java.util.ArrayList;
import java.util.List;

public class BuildLine {

    public void BuildAreaLine(Location centerLocation){
        World world = centerLocation.getWorld();

        int sizeX = CanNotBuildHere.getInstance().areaDefaultSize + 3; // x 방향 크기
        int sizeY = CanNotBuildHere.getInstance().areaDefaultHeight; // y 방향 크기
        int sizeZ = CanNotBuildHere.getInstance().areaDefaultSize + 3; // z 방향 크기

        int startX = centerLocation.getBlockX() - sizeX / 2;
        int startY = centerLocation.getBlockY();
        int startZ = centerLocation.getBlockZ() - sizeZ / 2;

        int[][] corners = {
                {startX, startY, startZ}, // 좌상단 앞
                {startX, startY, startZ + sizeZ - 1}, // 좌상단 뒤
                {startX + sizeX - 1, startY, startZ}, // 우상단 앞
                {startX + sizeX - 1, startY, startZ + sizeZ - 1}, // 우상단 뒤
                {startX, startY + sizeY - 1, startZ}, // 좌하단 앞
                {startX, startY + sizeY - 1, startZ + sizeZ - 1}, // 좌하단 뒤
                {startX + sizeX - 1, startY + sizeY - 1, startZ}, // 우하단 앞
                {startX + sizeX - 1, startY + sizeY - 1, startZ + sizeZ - 1} // 우하단 뒤
        };

        // 각 점의 옆에 있는 점들을 이어줌
        for (int i = 0; i < corners.length; i++) {
            for (int j = i + 1; j < corners.length; j++) {
                int[] startCorner = corners[i];
                int[] endCorner = corners[j];

                // 대각선 모서리가 아닌 경우에만 선을 그리도록
                if (isAdjacentCorner(startCorner, endCorner)) {
                    drawLine(world, startCorner, endCorner);
                }
            }
        }
    }

    // 두 모서리가 서로 인접한지 확인하는 메서드
    private boolean isAdjacentCorner(int[] corner1, int[] corner2) {
        // x, y, z 좌표 중에서 한 좌표 값만 차이가 나는 경우 서로 인접한 모서리임
        int differentCoordinateCount = 0;
        for (int i = 0; i < 3; i++) {
            if (corner1[i] != corner2[i]) {
                differentCoordinateCount++;
            }
        }
        return differentCoordinateCount == 1;
    }

    // 두 모서리를 연결하는 선을 그리는 메서드
    private void drawLine(World world, int[] startCorner, int[] endCorner) {
        Location startLocation = new Location(world, startCorner[0], startCorner[1], startCorner[2]);
        Location endLocation = new Location(world, endCorner[0], endCorner[1], endCorner[2]);

        int dx = Math.abs(endCorner[0] - startCorner[0]);
        int dy = Math.abs(endCorner[1] - startCorner[1]);
        int dz = Math.abs(endCorner[2] - startCorner[2]);

        int maxDistance = Math.max(Math.max(dx, dy), dz);

        double deltaX = (endCorner[0] - startCorner[0]) / (double) maxDistance;
        double deltaY = (endCorner[1] - startCorner[1]) / (double) maxDistance;
        double deltaZ = (endCorner[2] - startCorner[2]) / (double) maxDistance;

        for (int i = 0; i <= maxDistance; i++) {
            Location blockLocation = new Location(world,
                    startLocation.getX() + i * deltaX,
                    startLocation.getY() + i * deltaY,
                    startLocation.getZ() + i * deltaZ);
            Block block = world.getBlockAt(blockLocation);
            block.setType(Material.RED_STAINED_GLASS);
        }
    }
}
