package xyz.hstudio.hstudiolibrary.nms;

import net.minecraft.server.v1_9_R2.ChatComponentText;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_9_R2 implements IMcAccessor {

    @Override
    public void sendActionBar(final Player player, final String msg) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(msg), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}