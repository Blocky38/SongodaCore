package com.songoda.core.nms.v1_8_R2.world;

import com.songoda.core.nms.ReflectionUtils;
import com.songoda.core.nms.v1_8_R2.world.spawner.BBaseSpawnerImpl;
import com.songoda.core.nms.world.BBaseSpawner;
import com.songoda.core.nms.world.SItemStack;
import com.songoda.core.nms.world.SSpawner;
import com.songoda.core.nms.world.SWorld;
import com.songoda.core.nms.world.WorldCore;
import net.minecraft.server.v1_8_R2.Block;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.Chunk;
import net.minecraft.server.v1_8_R2.ChunkSection;
import net.minecraft.server.v1_8_R2.IBlockData;
import net.minecraft.server.v1_8_R2.MobSpawnerAbstract;
import net.minecraft.server.v1_8_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R2.CraftChunk;
import org.bukkit.inventory.ItemStack;

public class WorldCoreImpl implements WorldCore {
    @Override
    public SSpawner getSpawner(CreatureSpawner spawner) {
        return new SSpawnerImpl(spawner.getLocation());
    }

    @Override
    public SSpawner getSpawner(Location location) {
        return new SSpawnerImpl(location);
    }

    @Override
    public SItemStack getItemStack(ItemStack item) {
        return new SItemStackImpl(item);
    }

    @Override
    public SWorld getWorld(World world) {
        return new SWorldImpl();
    }

    @Override
    public BBaseSpawner getBaseSpawner(CreatureSpawner spawner) throws NoSuchFieldException, IllegalAccessException {
        Object cTileEntity = ReflectionUtils.getFieldValue(spawner, "spawner");

        return new BBaseSpawnerImpl((MobSpawnerAbstract) ReflectionUtils.getFieldValue(cTileEntity, "a"));
    }

    /**
     * Method is based on {@link WorldServer#h()}.
     */
    @SuppressWarnings("JavadocReference")
    @Override
    public void randomTickChunk(org.bukkit.Chunk bukkitChunk, int tickAmount) throws NoSuchFieldException, IllegalAccessException {
        Chunk chunk = ((CraftChunk) bukkitChunk).getHandle();

        if (tickAmount <= 0) {
            return;
        }

        int k = chunk.locX * 16;
        int l = chunk.locZ * 16;

        for (ChunkSection cSection : chunk.getSections()) {
            if (cSection != null && cSection.shouldTick()) {
                for (int i = 0; i < tickAmount; ++i) {
                    int m = (int) ReflectionUtils.getFieldValue(chunk.world, "m");

                    m = m * 3 + 1013904223;
                    ReflectionUtils.setFieldValue(chunk.world, "m", m);

                    int i2 = m >> 2;
                    int j2 = i2 & 15;
                    int k2 = i2 >> 8 & 15;
                    int l2 = i2 >> 16 & 15;

                    BlockPosition blockposition2 = new BlockPosition(j2 + k, l2 + cSection.getYPosition(), k2 + l);
                    IBlockData iblockdata = cSection.getType(j2, l2, k2);
                    Block block = iblockdata.getBlock();

                    if (block.isTicking()) {
                        block.a(chunk.world, blockposition2, iblockdata, chunk.world.random);
                    }
                }
            }
        }
    }
}
