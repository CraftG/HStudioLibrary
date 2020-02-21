package xyz.hstudio.hstudiolibrary.utils;

import org.bukkit.entity.Player;
import xyz.hstudio.hstudiolibrary.nms.McAccessor;

public class PlayerUtils {

    public static void sendActionBar(final String msg, final Player... players) {
        for (Player player : players) {
            McAccessor.INSTANCE.sendActionBar(player, msg);
        }
    }
}