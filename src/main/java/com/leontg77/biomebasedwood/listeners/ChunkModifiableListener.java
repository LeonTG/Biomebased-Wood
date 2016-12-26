/*
 * Project: Biomebased-Wood
 * Class: com.leontg77.biomebasedwood.listeners.ChunkModifiableListener
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
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * Chunk Modifiable listener class.
 *
 * @author LeonTG77
 */
public class ChunkModifiableListener implements Listener {
    private final Main plugin;

    public ChunkModifiableListener(Main plugin) {
        this.plugin = plugin;
    }

    private boolean physics = true;

    @EventHandler
    public void on(BlockPhysicsEvent event) {
        if (!physics) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(ChunkModifiableEvent event) {
        Chunk chunk = event.getChunk();
        physics = false; // for doors not to pop off during setting

        // Declaring variables before loop instead of declare+assign in loop
        // Fuck knows if that helps, but maybe it does
        Block block;
        Biome biome;
        byte oldData;
        Material oldMaterial;

        byte logData;
        byte woodData;

        Material logMaterial;
        Material fenceMaterial;
        Material fenceGateMaterial;
        Material doorMaterial;
        Material stairMaterial;

        // y < 128: There will probably not be a village/etc. above y = 128
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    // Yes this is ugly compared to a handle method,
                    // but I don't think the JVM inlines so long methods

                    block = chunk.getBlock(x, y, z);

                    // fence because blacksmiths aren't the only with double slab but the other doesn't have a fence.
                    if (block.getType() == Material.DOUBLE_STEP && plugin.getDurability(block) == 0) {
                        if (plugin.hasBlockNearby(Material.FENCE, block.getLocation()) ||
                                plugin.hasBlockNearby(Material.SPRUCE_FENCE, block.getLocation()) ||
                                plugin.hasBlockNearby(Material.BIRCH_FENCE, block.getLocation()) ||
                                plugin.hasBlockNearby(Material.JUNGLE_FENCE, block.getLocation()) ||
                                plugin.hasBlockNearby(Material.ACACIA_FENCE, block.getLocation()) ||
                                plugin.hasBlockNearby(Material.DARK_OAK_FENCE, block.getLocation())) {

                            block.setType(Material.ANVIL);
                            continue;
                        }
                    }

                    biome = block.getBiome();
                    oldData = block.getData();
                    oldMaterial = block.getType();

                    logMaterial = Material.LOG;
                    switch (biome) {
                        case BIRCH_FOREST:
                        case BIRCH_FOREST_HILLS:
                        case BIRCH_FOREST_HILLS_MOUNTAINS:
                        case BIRCH_FOREST_MOUNTAINS:
                            logData = 2;
                            woodData = 2;
                            fenceMaterial = Material.BIRCH_FENCE;
                            fenceGateMaterial = Material.BIRCH_FENCE_GATE;
                            doorMaterial = Material.BIRCH_DOOR;
                            stairMaterial = Material.BIRCH_WOOD_STAIRS;
                            break;
                        case JUNGLE:
                        case JUNGLE_EDGE:
                        case JUNGLE_EDGE_MOUNTAINS:
                        case JUNGLE_HILLS:
                        case JUNGLE_MOUNTAINS:
                            logData = 3;
                            woodData = 3;
                            fenceMaterial = Material.JUNGLE_FENCE;
                            fenceGateMaterial = Material.JUNGLE_FENCE_GATE;
                            doorMaterial = Material.JUNGLE_DOOR;
                            stairMaterial = Material.JUNGLE_WOOD_STAIRS;
                            break;
                        case ROOFED_FOREST:
                        case ROOFED_FOREST_MOUNTAINS:
                            logData = 1;
                            woodData = 5;
                            logMaterial = Material.LOG_2;
                            fenceMaterial = Material.DARK_OAK_FENCE;
                            fenceGateMaterial = Material.DARK_OAK_FENCE_GATE;
                            doorMaterial = Material.DARK_OAK_DOOR;
                            stairMaterial = Material.DARK_OAK_STAIRS;
                            break;
                        case SAVANNA:
                        case SAVANNA_MOUNTAINS:
                        case SAVANNA_PLATEAU:
                        case SAVANNA_PLATEAU_MOUNTAINS:
                            logData = 0;
                            woodData = 4;
                            logMaterial = Material.LOG_2;
                            fenceMaterial = Material.ACACIA_FENCE;
                            fenceGateMaterial = Material.ACACIA_FENCE_GATE;
                            doorMaterial = Material.ACACIA_DOOR;
                            stairMaterial = Material.ACACIA_STAIRS;
                            break;
                        case TAIGA:
                        case TAIGA_HILLS:
                        case TAIGA_MOUNTAINS:
                        case MEGA_SPRUCE_TAIGA:
                        case MEGA_SPRUCE_TAIGA_HILLS:
                        case MEGA_TAIGA:
                        case MEGA_TAIGA_HILLS:
                        case COLD_TAIGA:
                        case COLD_TAIGA_HILLS:
                        case COLD_TAIGA_MOUNTAINS:
                            logData = 1;
                            woodData = 1;
                            fenceMaterial = Material.SPRUCE_FENCE;
                            fenceGateMaterial = Material.SPRUCE_FENCE_GATE;
                            doorMaterial = Material.SPRUCE_DOOR;
                            stairMaterial = Material.SPRUCE_WOOD_STAIRS;
                            break;
                        case DESERT:
                        case DESERT_HILLS:
                        case DESERT_MOUNTAINS:
                            if (block.getType() == Material.LOG_2) {
                                continue;
                            }

                            if (block.getType() == Material.LOG) {
                                block.setType(Material.SANDSTONE);
                                continue;
                            }

                            if (block.getType() == Material.WOOD) {
                                if (block.getLocation().getBlockY() > 55) {
                                    block.setType(Material.SANDSTONE);
                                }
                                block.setData((byte) 2);
                                continue;
                            }

                            logData = 2;
                            woodData = 2;
                            fenceMaterial = Material.BIRCH_FENCE;
                            fenceGateMaterial = Material.BIRCH_FENCE_GATE;
                            doorMaterial = Material.BIRCH_DOOR;
                            stairMaterial = Material.SANDSTONE_STAIRS;
                            break;
                        default:
                            continue;
                    }

                    switch (oldMaterial) {
                        case LOG:
                        case LOG_2:
                            if (hasLeavesNearby(block)) {
                                break;
                            }

                            if (oldMaterial != logMaterial) {
                                block.setType(logMaterial);
                            }

                            block.setData(logData);
                            block.getState().update(true);
                            break;
                        case WOOD:
                            block.setData(woodData);
                            block.getState().update(true);
                            break;
                        case FENCE:
                            block.setType(fenceMaterial);
                            block.setData(oldData);
                            break;
                        case FENCE_GATE:
                            block.setType(fenceGateMaterial);
                            block.setData(oldData);
                            break;
                        case WOODEN_DOOR:
                            block.setType(doorMaterial);
                            block.setData(oldData);
                            break;
                        case WOOD_STAIRS:
                            block.setType(stairMaterial);
                            block.setData(oldData);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        physics = true;
    }

    private boolean hasLeavesNearby(Block start) {
        for (Block vein : plugin.getVein(start)) {
            for (Block loop : plugin.getNearby(vein)) {
                if (loop.getType() == Material.LEAVES || loop.getType() == Material.LEAVES_2) {
                    return true;
                }
            }
        }

        return false;
    }
}