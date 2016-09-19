package com.leontg77.biomebasedwood.listeners;

import com.leontg77.biomebasedwood.Main;
import com.leontg77.biomebasedwood.events.ChunkModifiableEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Chunk populate listener class.
 *
 * @author LeonTG77
 */
public class ChunkPopulateListener implements Listener {
    private final Main plugin;

    public ChunkPopulateListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(ChunkPopulateEvent event) {
        Chunk chunk = event.getChunk();
        World world = event.getWorld();

        String worldName = world.getName();

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorld(worldName);

                if (world == null) { // World was unloaded
                    return;
                }

                Chunk chunk = world.getChunkAt(chunkX, chunkZ);
                Bukkit.getPluginManager().callEvent(new ChunkModifiableEvent(chunk));
            }
        }.runTaskLater(plugin, 400L);
    }
}