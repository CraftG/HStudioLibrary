package xyz.hstudio.hstudiolibrary.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockUtils {

    /**
     * 异步获取某位置的方块
     * 如果区块未加载则返回null
     *
     * @param loc Location
     */
    public static Block getBlock(final Location loc) {
        if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
            return loc.getBlock();
        }
        return null;
    }
}