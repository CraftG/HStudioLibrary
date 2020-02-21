package xyz.hstudio.hstudiolibrary.nms;

import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_11_R1 implements IMcAccessor {

    @Override
    public void sendActionBar(final Player player, final String msg) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(msg), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}