package xyz.hstudio.hstudiolibrary.nms;

import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.ChatMessageType;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_15_R1 implements IMcAccessor {

    @Override
    public void sendActionBar(final Player player, final String msg) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(msg), ChatMessageType.GAME_INFO);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}