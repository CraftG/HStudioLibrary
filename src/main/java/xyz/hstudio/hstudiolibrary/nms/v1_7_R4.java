package xyz.hstudio.hstudiolibrary.nms;

import net.minecraft.server.v1_7_R4.ChatComponentText;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_7_R4 implements IMcAccessor {

    @Override
    public void sendActionBar(final Player player, final String msg) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(msg), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}