/*
 * Project: Biomebased-Wood
 * Class: com.leontg77.biomebasedwood.listeners.ChunkPopulateListener
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Leon Vaktskjold <leontg77@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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