package com.songoda.core.nms.v1_18_R1.entity;

import com.songoda.core.nms.entity.NMSPlayer;
import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSPlayerImpl implements NMSPlayer {
    @Override
    public void sendPacket(Player p, Object packet) {
        ((CraftPlayer) p).getHandle().b.a((Packet<?>) packet);
    }
}
